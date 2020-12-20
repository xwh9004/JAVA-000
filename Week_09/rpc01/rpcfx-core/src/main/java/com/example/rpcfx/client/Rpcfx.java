package com.example.rpcfx.client;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.example.rpcfx.api.RpcfxRequest;
import com.example.rpcfx.api.RpcfxResponse;
import com.example.rpcfx.proxy.ByteBuddyProxy;
import com.example.rpcfx.proxy.JdkDynamicProxy;
import com.example.rpcfx.proxy.RemoteProxy;
import com.example.rpcfx.proxy.RpcfxInvocationHandler;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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
