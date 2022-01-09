package com.example.demo.plugin.transform;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.println;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.api.variant.VariantInfo;
import com.android.build.gradle.AppExtension;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.utils.FileUtils;
import com.example.demo.plugin.Constants;
import com.example.demo.plugin.visitor.OkhttpInterceptorClassVisitor;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class NetInterceptTransform extends Transform implements Plugin<Project> {

    private static final int ASM_VERSION = Opcodes.ASM6;

    @Override
    public void apply(Project target) {
        println(Constants.PLUGIN_TAG + "/" + "apply, this log from NetInterceptTransform");
        //此插件只在非release包下生效
        if (!isReleaseTask(target)) {
            AppExtension appExtension = target.getExtensions().getByType(AppExtension.class);
            appExtension.registerTransform(this);
        } else {
            println(Constants.PLUGIN_TAG + "/" + "release task!");
        }
    }

    @Override
    public boolean applyToVariant(VariantInfo variant) {
        if (variant == null) {
            return false;
        }
        boolean isApply = variant.isDebuggable() && (!variant.getBuildTypeName().contains("release") || !variant.getBuildTypeName().contains("Release"));
        println(Constants.PLUGIN_TAG + "/" + "applyToVariant: isApply:" + isApply);
        return isApply;
    }

    @Override
    public String getName() {
        return NetInterceptTransform.class.getSimpleName();
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        Collection<TransformInput> inputs = transformInvocation.getInputs();
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();

        if (!isIncremental() && (outputProvider != null)) {
            outputProvider.deleteAll();
        }

        if (inputs == null) {
            return;
        }
        inputs.forEach(new Consumer<TransformInput>() {
            @Override
            public void accept(TransformInput transformInput) {
                transformInput.getDirectoryInputs().forEach(new Consumer<DirectoryInput>() {
                    @Override
                    public void accept(DirectoryInput directoryInput) {
                        handleDirectoryInput(directoryInput, outputProvider);
                    }
                });

                transformInput.getJarInputs().forEach(new Consumer<JarInput>() {
                    @Override
                    public void accept(JarInput jarInput) {
                        try {
                            handleJarInput(jarInput, outputProvider);
                        } catch (Exception e) {
                            println(Constants.PLUGIN_TAG + "/" + "accept, error:" + e.getMessage());
                        }
                    }
                });
            }
        });
    }

    private void handleDirectoryInput(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        if (directoryInput.getFile().isDirectory()) {
            FileUtils.getAllFiles(directoryInput.getFile()).forEach(new Consumer<File>() {
                @Override
                public void accept(File file) {
                    String fileName = file.getName();
                    println(Constants.PLUGIN_TAG + "/" + "getAllFiles:" + fileName);
                    if (fileName.endsWith(".class")
                            && !fileName.equals("R.class")
                            && !fileName.startsWith("R\\$")
                            && !fileName.equals("BuildConfig.class")) {
                        try {
                            String classPath = file.getAbsolutePath();

                            ClassReader classReader = new ClassReader(new FileInputStream(file));
                            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                            OkhttpInterceptorClassVisitor visitor = new OkhttpInterceptorClassVisitor(ASM_VERSION, classWriter);
                            classReader.accept(visitor, ClassReader.EXPAND_FRAMES);

                            byte[] bytes = classWriter.toByteArray();
                            FileOutputStream fileOutputStream = new FileOutputStream(classPath);
                            fileOutputStream.write(bytes);
                            fileOutputStream.close();
                        } catch (Exception e) {
                            println(Constants.PLUGIN_TAG + "/" + "accept, error:" + e.getMessage());
                        }
                    }
                }
            });
        }

        try {
            if (outputProvider != null) {
                File file = outputProvider.getContentLocation(
                        directoryInput.getName(),
                        directoryInput.getContentTypes(),
                        directoryInput.getScopes(),
                        Format.DIRECTORY);
                FileUtils.copyDirectoryToDirectory(directoryInput.getFile(), file);
            }
        } catch (Exception e) {
            println(Constants.PLUGIN_TAG, "accept, error:" + e.getMessage());
        }


    }

    private void handleJarInput(JarInput jarInput, TransformOutputProvider outputProvider) throws Exception {
        File jarInputFile = jarInput.getFile();
        if (jarInputFile.getAbsolutePath().endsWith(".jar")) {
            String jarName = jarInput.getName();
            String md5Name = DigestUtils.md5Hex(jarInputFile.getAbsolutePath());

            if (jarName.endsWith(".jar")) {
                jarName = jarName.substring(0, jarName.length() - 4);
            }
            JarFile jarFile = new JarFile(jarInputFile);
            Enumeration<JarEntry> enumeration = jarFile.entries();
            File tempFile = new File(jarInputFile.getParent() + File.separator + "classes_temp.jar");

            //避免上次缓存的被重复插入
            if (tempFile.exists()) {
                tempFile.delete();
            }

            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(tempFile));

            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = enumeration.nextElement();
                String entryName = jarEntry.getName();
                ZipEntry zipEntry = new ZipEntry(entryName);

                InputStream inputStream = jarFile.getInputStream(jarEntry);

                if (entryName.endsWith(".class")
                        && !entryName.startsWith("R\\$")
                        && !entryName.equals("R.class")
                        && !entryName.equals("BuildConfig.class")) {
                    jarOutputStream.putNextEntry(zipEntry);
                    ClassReader classReader = new ClassReader(IOUtils.toByteArray(inputStream));
                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                    OkhttpInterceptorClassVisitor visitor = new OkhttpInterceptorClassVisitor(ASM_VERSION, classWriter);
                    classReader.accept(visitor, ClassReader.EXPAND_FRAMES);

                    byte[] bytes = classWriter.toByteArray();
                    jarOutputStream.write(bytes);
                } else {
                    jarOutputStream.putNextEntry(zipEntry);
                    jarOutputStream.write(IOUtils.toByteArray(inputStream));
                }
                jarOutputStream.closeEntry();
            }

            jarOutputStream.close();
            jarFile.close();

            if (outputProvider != null) {
                File fileDest = outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.getContentTypes(),
                        jarInput.getScopes(),
                        Format.JAR);
                FileUtils.copyFile(tempFile, fileDest);
                tempFile.delete();
            }
        } else {
            if (outputProvider != null) {
                File destFile = outputProvider.getContentLocation(jarInput.getName(),
                        jarInput.getContentTypes(),
                        jarInput.getScopes(),
                        Format.JAR);
                FileUtils.copyFile(jarInput.getFile(), destFile);
            }
        }
    }

    private boolean isReleaseTask(Project project) {
        return project.getGradle().getStartParameter().getTaskNames().stream().anyMatch(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("release") || s.contains("Release");
            }
        });
    }
}
