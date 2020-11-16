package com.example.aop.byteBuddy;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class SchoolProxy {

    public static void dang(@Origin Method method,
                            // 调用该注解后的Runnable/Callable，会导致调用被代理的非抽象父方法
                            @SuperCall Callable<?> callable) {

        try {
            System.out.println("上课了....");
            callable.call();
            System.out.println("下课了....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ding(@Origin Method method,
                            // 调用该注解后的Runnable/Callable，会导致调用被代理的非抽象父方法
                            @SuperCall Callable<?> callable) {

        try {
            System.out.println("上课了....");
            callable.call();
            System.out.println("下课了....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
