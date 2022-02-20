package com.example.demo;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class DemoMethodVisitor extends MethodVisitor {

    public DemoMethodVisitor(int api) {
        super(api);
    }

    public DemoMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

    @Override
    public void visitCode() {
        super.visitCode();
        System.out.println("begin visit method");
        mv.visitFieldInsn(Opcodes.GETSTATIC,
                "java/lang/System",
                "out",
                "Ljava/io/PrintStream;");
        mv.visitLdcInsn("invoke success!");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream",
                "println",
                "(Ljava/lang/String;)V",
                false);
    }
}
