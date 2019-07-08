package com.github.crack;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class MyMethodVistor extends AdviceAdapter {
    public MyMethodVistor(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM7, mv, access, name, desc);
    }

    @Override
    protected void onMethodEnter() {
        mv.visitIntInsn(SIPUSH, 2026);
        mv.visitInsn(IRETURN);
    }
}