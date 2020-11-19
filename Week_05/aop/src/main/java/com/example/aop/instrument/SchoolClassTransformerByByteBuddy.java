package com.example.aop.instrument;

import com.example.aop.School;
import com.example.aop.byteBuddy.SchoolProxy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 9:35 on 2020/11/19
 * @version V0.1
 * @classNmae SchoolClassTransformerByByteBuddy
 */
public class SchoolClassTransformerByByteBuddy implements AgentBuilder.Transformer {

    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
        return builder

                .method(ElementMatchers.named("ding")) // 拦截任意方法
                .intercept(MethodDelegation.to(SchoolProxy.class)); // 委托
    }
}
