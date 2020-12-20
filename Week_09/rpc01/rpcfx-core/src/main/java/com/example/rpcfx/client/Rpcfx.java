package com.example.rpcfx.client;


import com.alibaba.fastjson.parser.ParserConfig;
import com.example.rpcfx.proxy.ByteBuddyProxy;
import com.example.rpcfx.proxy.RemoteProxy;

public final class Rpcfx {

//    public static RemoteProxy  proxy = new JdkDynamicProxy();
public static RemoteProxy  proxy = new ByteBuddyProxy();
    static {
        ParserConfig.getGlobalInstance().addAccept("com.example");
    }

    public static <T> T create(final Class<T> serviceClass, final String url) throws Exception {
        return proxy.newInstance(serviceClass, url);
    }
}
