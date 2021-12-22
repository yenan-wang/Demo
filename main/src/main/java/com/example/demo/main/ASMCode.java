package com.example.demo.main;

import com.example.demo.network.plugin.OkHttpHook;

import okhttp3.OkHttpClient;

public class ASMCode extends OkHttpClient {

    public ASMCode() {
        OkHttpHook.getInstance().addInterceptor(this);
    }

    //第10行代码的字节码ASM形式
    /*
    methodVisitor.visitMethodInsn(INVOKESTATIC, "com/example/demo/network/plugin/OkHttpHook", "getInstance", "()Lcom/example/demo/network/plugin/OkHttpHook;", false);
    methodVisitor.visitVarInsn(ALOAD, 0);
    methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "com/example/demo/network/plugin/OkHttpHook", "addInterceptor", "(Lokhttp3/OkHttpClient;)V", false);
    */
}
