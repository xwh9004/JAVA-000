package com.example.rpcfx.proxy;

public interface RemoteProxy {

   <T> T newInstance(final Class<T> serviceClass, final String url);
}
