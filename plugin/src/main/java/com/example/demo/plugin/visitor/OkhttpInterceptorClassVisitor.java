package com.example.demo.plugin.visitor;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.println;

import com.example.demo.plugin.Constants;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class OkhttpInterceptorClassVisitor extends ClassVisitor {

    private String mClassName;

    public OkhttpInterceptorClassVisitor(int api) {
        super(api);
    }

    public OkhttpInterceptorClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        mClassName = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        if ("okhttp3/OkHttpClient".equals(mClassName)) {
            if ("<init>".equals(name) && !descriptor.equals("()V")) {
                println(Constants.PLUGIN_TAG + "/" + "visitMethod, find className:" + mClassName);
                return new OkhttpInterceptorMethodVisitor(api, methodVisitor);
            }
        }
        return methodVisitor;
    }
}
