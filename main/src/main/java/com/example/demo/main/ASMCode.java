package com.example.demo.main;

import com.example.demo.main.demo.OkHttpClient1;
import com.example.demo.network.plugin.OkHttpHook;

import okhttp3.OkHttpClient;

public class ASMCode extends OkHttpClient {

    public ASMCode() {
        OkHttpHook.getInstance().addInterceptor(this);
    }

    public OkHttpClient function() {
        Builder builder = new Builder();
        return OkHttpHook.getInstance().addInterceptorWithReturn(new OkHttpClient1(builder));
    }

    public OkHttpClient function1() {
        return OkHttpHook.getInstance().addInterceptorWithReturn(new OkHttpClient());
    }

    //第11行代码的字节码ASM形式
    /*
    methodVisitor.visitMethodInsn(INVOKESTATIC, "com/example/demo/network/plugin/OkHttpHook", "getInstance", "()Lcom/example/demo/network/plugin/OkHttpHook;", false);
    methodVisitor.visitVarInsn(ALOAD, 0);
    methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "com/example/demo/network/plugin/OkHttpHook", "addInterceptor", "(Lokhttp3/OkHttpClient;)V", false);
    */

    //第16行代码的字节码ASM形式
    /*
    methodVisitor.visitMethodInsn(INVOKESTATIC, "com/example/demo/network/plugin/OkHttpHook", "getInstance", "()Lcom/example/demo/network/plugin/OkHttpHook;", false);
    methodVisitor.visitTypeInsn(NEW, "com/example/demo/main/demo/OkHttpClient1");
    methodVisitor.visitInsn(DUP);
    methodVisitor.visitVarInsn(ALOAD, 1);
    methodVisitor.visitMethodInsn(INVOKESPECIAL, "com/example/demo/main/demo/OkHttpClient1", "<init>", "(Lokhttp3/OkHttpClient$Builder;)V", false);
    methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "com/example/demo/network/plugin/OkHttpHook", "addInterceptorWithReturn", "(Lokhttp3/OkHttpClient;)Lokhttp3/OkHttpClient;", false);
    methodVisitor.visitInsn(ARETURN);
    */

    //第20行代码的字节码ASM形式
    /*
    methodVisitor.visitMethodInsn(INVOKESTATIC, "com/example/demo/network/plugin/OkHttpHook", "getInstance", "()Lcom/example/demo/network/plugin/OkHttpHook;", false);
    methodVisitor.visitTypeInsn(NEW, "okhttp3/OkHttpClient");
    methodVisitor.visitInsn(DUP);
    methodVisitor.visitMethodInsn(INVOKESPECIAL, "okhttp3/OkHttpClient", "<init>", "()V", false);
    methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "com/example/demo/network/plugin/OkHttpHook", "addInterceptorWithReturn", "(Lokhttp3/OkHttpClient;)Lokhttp3/OkHttpClient;", false);
    methodVisitor.visitInsn(ARETURN);
    */
}
