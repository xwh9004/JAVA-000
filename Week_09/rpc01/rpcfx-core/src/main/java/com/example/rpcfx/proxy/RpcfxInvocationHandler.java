package com.example.rpcfx.proxy;

import com.alibaba.fastjson.JSON;
import com.example.rpcfx.api.RpcfxRequest;
import com.example.rpcfx.api.RpcfxResponse;
import com.example.rpcfx.client.Client;
import com.example.rpcfx.client.httpClient.RpcHttpClient;
import com.example.rpcfx.client.nettyClient.RpcNettyClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RpcfxInvocationHandler implements InvocationHandler {

    private  Client client = new RpcNettyClient();

    private final Class<?> serviceClass;
    private final String url;
    public <T> RpcfxInvocationHandler(Class<T> serviceClass, String url) {
        this.serviceClass = serviceClass;
        this.url = url;
    }

    // 可以尝试，自己去写对象序列化，二进制还是文本的，，，rpcfx是xml自定义序列化、反序列化，json: code.google.com/p/rpcfx
    // int byte char float double long bool
    // [], data class

    @Override
    public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
        RpcfxRequest request = new RpcfxRequest();
        request.setServiceClass(this.serviceClass.getName());
        request.setMethod(method.getName());
        request.setParams(params);

        RpcfxResponse response = post(request, url);

        // 这里判断response.status，处理异常
        // 考虑封装一个全局的RpcfxException

        return JSON.parse(response.getResult().toString());
    }

    private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {


        // 1.可以复用client
        // 2.尝试使用httpclient或者netty client
      return    client.invoke(req,url);

    }
}
