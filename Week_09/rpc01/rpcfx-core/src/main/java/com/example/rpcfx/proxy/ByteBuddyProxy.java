package com.example.rpcfx.proxy;

import com.example.rpcfx.exception.RpcProxyException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;

public class ByteBuddyProxy implements RemoteProxy {
    @Override
    public <T> T newInstance(Class<T> serviceClass, String url){
        try {
            T proxy = new ByteBuddy()
              .subclass(serviceClass)
              .method(ElementMatchers.anyOf(serviceClass.getMethods()))
              .intercept(InvocationHandlerAdapter.of(new RpcfxInvocationHandler(serviceClass,url)))
              .make()
              .load(Thread.currentThread().getContextClassLoader())
              .getLoaded()
              .getDeclaredConstructor().newInstance();
            return proxy;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        throw new RpcProxyException(String.format("can not generate %s proxy instance",serviceClass));
    }
}
