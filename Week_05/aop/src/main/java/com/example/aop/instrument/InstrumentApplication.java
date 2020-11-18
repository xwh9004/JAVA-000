package com.example.aop.instrument;

import com.example.aop.School;
import com.example.aop.byteBuddy.SchoolProxy;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 14:20 on 2020/11/18
 * @version V0.1
 * @classNmae InstrumentApplication
 */
public class InstrumentApplication {

    public static void premain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException, ClassNotFoundException {
        System.out.println("JVM load premain");
//        inst.addTransformer(new SchoolClassTransformer());
        redefineClass(inst);
        System.out.println("JVM added School proxy!");
    }

    private static void redefineClass(Instrumentation inst){
        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer(){

            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                    TypeDescription typeDescription,
                                                    ClassLoader classLoader,
                                                    JavaModule module) {

                return builder
                        .method(ElementMatchers.named("ding")) // 拦截任意方法
                        .intercept(MethodDelegation.to(SchoolProxy.class)); // 委托

            }
        };


        new AgentBuilder
                .Default()
                .type(ElementMatchers.nameStartsWith("com.example.aop.School")) // 指定需要拦截的类
                .transform(transformer)
                .installOn(inst);

    }
}
