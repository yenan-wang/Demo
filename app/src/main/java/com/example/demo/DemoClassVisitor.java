package com.example.demo;

import android.util.Log;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class DemoClassVisitor extends ClassVisitor {
    private String mClassName = "";

    public DemoClassVisitor(int api) {
        super(api);
    }

    public DemoClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        mClassName = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        //获取当前的MethodVisitor
        MethodVisitor superVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        //如果是我们要找的Hello类里的void main(String[] args){} 方法，则进行自定义MethodVisitor
        if ("Hello".equals(mClassName) && ("main".equals(name) && "([Ljava/lang/String;)V".equals(descriptor))) {
            System.out.println("find method success!");
            MethodVisitor methodVisitor = new DemoMethodVisitor(api, superVisitor);
            return methodVisitor;
        }
        //否则就返回原本的MethodVisitor
        return superVisitor;
    }
}
