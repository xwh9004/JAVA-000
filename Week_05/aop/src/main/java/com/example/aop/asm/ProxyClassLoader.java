package com.example.aop.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.InputStream;

import static org.objectweb.asm.Opcodes.ASM8;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 17:12 on 2020/11/16
 * @version V0.1
 * @classNmae ProxyClassLoader
 */
public class ProxyClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {

            if (!name.equals("com.example.aop.School"))
                return super.loadClass(name);
            try {
                ClassWriter cw = new ClassWriter(0);
                //
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("com/example/aop/School.class");
                ClassReader reader = new ClassReader(is);
                reader.accept(new SchoolAsmProxy(cw), ClassReader.SKIP_DEBUG);

                byte[] code = cw.toByteArray();
                return this.defineClass(name, code, 0, code.length);
            } catch (Throwable e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }

}
