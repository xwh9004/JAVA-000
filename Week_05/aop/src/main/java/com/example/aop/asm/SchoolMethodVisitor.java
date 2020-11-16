package com.example.aop.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 17:02 on 2020/11/16
 * @version V0.1
 * @classNmae SchoolMethodVisitor
 */
public class SchoolMethodVisitor extends MethodVisitor {
    public SchoolMethodVisitor(MethodVisitor mv) {
        super(Opcodes.ASM8, mv);
    }

    @Override
    public void visitCode() {
        System.out.println("visitCode ...");
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/example/aop/asm/Advice","before","()V",false);
        super.visitCode();
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        System.out.println("visitMethodInsn=" +name);
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }

    @Override
    public void visitInsn(int opcode) {
        System.out.println("visitInsn ...");
        if(opcode==Opcodes.RETURN){
            //方法返回前
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/example/aop/asm/Advice","after","()V",false);
        }
        super.visitInsn(opcode);
    }
}
