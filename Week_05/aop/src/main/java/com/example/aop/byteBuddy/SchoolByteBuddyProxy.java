package com.example.aop.byteBuddy;

import com.example.aop.ISchool;
import com.example.aop.School;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Method;

public class SchoolByteBuddyProxy {

    public static void main(String[] args) throws Exception {
//        proxyDing();
        proxyDing_interface();

    }

    /**
     * 代理类的方法
     * @throws Exception
     */
    public static void proxyDing() throws Exception {

        School proxy = new ByteBuddy()
                .subclass(School.class)
                .method(ElementMatchers.named("ding"))
                .intercept(MethodDelegation.to(SchoolProxy.class))
                .make()
                .load(Thread.currentThread().getContextClassLoader())
                .getLoaded()
                .newInstance();

        proxy.ding();
    }


    /**
     * 代理类的方法
     * @throws Exception
     */
    public static void proxyDing_interface() throws Exception {

        ISchool proxy = new ByteBuddy()
                .subclass(School.class).implement(ISchool.class)
                .method(ElementMatchers.named("ding"))
                .intercept(MethodDelegation.to(SchoolProxy.class))
                .make()
                .load(Thread.currentThread().getContextClassLoader())
                .getLoaded()
                .newInstance();

        proxy.ding();
    }
}
