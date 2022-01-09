package com.example.demo.plugin.visitor;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.println;

import com.example.demo.plugin.Constants;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class OkhttpInterceptorBuildVisitor extends MethodVisitor {
    public OkhttpInterceptorBuildVisitor(int api) {
        super(api);
    }

    public OkhttpInterceptorBuildVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

    @Override
    public void visitCode() {
        println(Constants.PLUGIN_TAG + "/" + "visitFieldInsn, success visit!!!");
        //The following code is equivalent to
        // "return OkHttpHook().getInstance().addInterceptorWithReturn(new OkHttpClient(this))"
        mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                "com/example/demo/network/plugin/OkHttpHook",
                "getInstance",
                "()Lcom/example/demo/network/plugin/OkHttpHook;",
                false);
        mv.visitTypeInsn(Opcodes.NEW, "okhttp3/OkHttpClient");
        mv.visitInsn(Opcodes.DUP);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL,
                "okhttp3/OkHttpClient",
                "<init>",
                "(Lokhttp3/OkHttpClient$Builder;)V",
                false);
        //mv.visitTypeInsn(Opcodes.CHECKCAST, "okhttp3/OkHttpClient");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "com/example/demo/network/plugin/OkHttpHook",
                "addInterceptorWithReturn",
                "(Lokhttp3/OkHttpClient;)Lokhttp3/OkHttpClient;",
                false);
        mv.visitInsn(Opcodes.ARETURN);
    }
}
