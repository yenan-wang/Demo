package com.example.demo.plugin.visitor;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.println;

import com.example.demo.plugin.Constants;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class OkhttpInterceptorMethodVisitor extends MethodVisitor {
    public OkhttpInterceptorMethodVisitor(int api) {
        super(api);
    }

    public OkhttpInterceptorMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        super.visitFieldInsn(opcode, owner, name, descriptor);
        println(Constants.PLUGIN_TAG + "/" + "visitFieldInsn, opcode:" + opcode + ", owner:" + owner + ", name:" + name + ", des:" + descriptor);
        if (opcode == Opcodes.PUTFIELD
                && owner.equals("okhttp3/OkHttpClient")
                && name.equals("networkInterceptors")
                && descriptor.equals("Ljava/util/List;")) {
            println(Constants.PLUGIN_TAG + "/" + "visitFieldInsn, success visit!!!");
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/example/demo/network/plugin/OkHttpHook", "getInstance", "()Lcom/example/demo/network/plugin/OkHttpHook;", false);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            //mv.visitTypeInsn(Opcodes.CHECKCAST, "okhttp3/OkHttpClient");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/example/demo/network/plugin/OkHttpHook", "addInterceptor", "(Lokhttp3/OkHttpClient;)V", false);
        }
    }
}
