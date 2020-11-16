package com.example.aop.jdk;

import com.example.aop.ISchool;
import com.example.aop.School;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SchoolDynamicProxy implements InvocationHandler {

    private ISchool target;

    SchoolDynamicProxy(ISchool target){
        this.target = target;
    }

    private void before(){
        System.out.println("上课了....");
    }


    private void after(){
        System.out.println("下课了....");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target, args);
        after();
        return result;
    }


    public static void main(String[] args) {
        //创建target对象
        ISchool school =new School();
       //创建target的代理对象的 InvocationHandler
        SchoolDynamicProxy studentDynamicProxy = new SchoolDynamicProxy(school);
        //创建代理对象
        ISchool proxy = (ISchool) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), school.getClass().getInterfaces(), studentDynamicProxy);
        proxy.ding();

    }
}
