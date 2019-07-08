package com.github.crack;


import org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class MyClassFileTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, //
                            String className, //
                            Class<?> classBeingRedefined, //
                            ProtectionDomain protectionDomain, //
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        String classname = "com/jclarity/censum/CensumStartupChecks";

        if (classname.equals(className)) {
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
            ClassVisitor cv = new MyClassVisitor(cw);
            cr.accept(cv, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);
            byte[] bytes = cw.toByteArray();
            writeByteArrayToFile(bytes, new File("./CensumStartupChecks-modify.class"));
            return bytes;

        }
        return classfileBuffer;
    }

    private static void writeByteArrayToFile(byte[] bytes, File file) {

        try (OutputStream out = new FileOutputStream(file, false)) {
            out.write(bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
