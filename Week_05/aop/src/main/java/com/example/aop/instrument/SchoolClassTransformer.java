package com.example.aop.instrument;

import com.example.aop.ISchool;
import com.example.aop.School;
import com.example.aop.asm.SchoolAsmProxy;
import com.example.aop.byteBuddy.SchoolProxy;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 14:36 on 2020/11/18
 * @version V0.1
 * @classNmae SchoolClassTransformer
 */
public class SchoolClassTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        System.out.println(className+" loaded!");
        if(!className.startsWith("com/example/aop/School")){
            return null;
        }
        //返回School代理
        byte[] bytes= new ByteBuddy()
                .subclass(School.class).name("com.example.aop.SchoolClient")
                .method(ElementMatchers.named("ding"))
                .intercept(MethodDelegation.to(SchoolProxy.class))
                .make().getBytes();
        System.out.println("classBeingRedefined"+classBeingRedefined);
        System.out.println(className+" changed!"  );
        return bytes;

    }

}
