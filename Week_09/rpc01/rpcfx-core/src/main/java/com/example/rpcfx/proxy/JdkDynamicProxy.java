package com.example.rpcfx.proxy;

import com.example.rpcfx.client.Rpcfx;

import java.lang.reflect.Proxy;

public class JdkDynamicProxy implements RemoteProxy {


     public  <T> T newInstance(final Class<T> serviceClass, final String url) {

       return   (T)Proxy.newProxyInstance(Rpcfx.class.getClassLoader(), new Class[]{serviceClass}, new RpcfxInvocationHandler(serviceClass, url));
    }
}
