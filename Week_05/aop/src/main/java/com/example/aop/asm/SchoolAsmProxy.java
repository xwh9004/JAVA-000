package com.example.aop.asm;

import com.example.aop.School;
import org.objectweb.asm.*;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 16:14 on 2020/11/16
 * @version V0.1
 * @classNmae SchoolAsmProxy
 */
public class SchoolAsmProxy extends ClassVisitor {


    public SchoolAsmProxy(final ClassVisitor cv) {
        super(Opcodes.ASM8, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("visitMethod "+name);
        if("ding".equals(name))  //此处的dang()即为需要修改的方法 ，修改方法內容
        {

            MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);//先得到原始的方法
            MethodVisitor newMethod =  new SchoolMethodVisitor(mv); //访问需要修改的方法
            return newMethod;
        }
        return cv.visitMethod(access, name, descriptor, signature, exceptions);

    }

    public static void main(String[] args) throws Exception {


      ProxyClassLoader classLoader =  new ProxyClassLoader();

      Class<?> clazz = classLoader.loadClass("com.example.aop.School");
      Method method = clazz.getDeclaredMethod("dang");
      method.setAccessible(true);
      method.invoke( clazz.newInstance());

    }
}
