package com.example.rpcfx.client.okHttp;

import com.alibaba.fastjson.JSON;
import com.example.rpcfx.api.RpcfxRequest;
import com.example.rpcfx.api.RpcfxResponse;
import com.example.rpcfx.client.Client;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RpcOkHttpClient implements Client {
    private static Map<String ,OkHttpClient> clients = new HashMap<>();

    public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");
    @Override
    public RpcfxResponse invoke(RpcfxRequest req, String url) throws IOException {
        String reqJson = JSON.toJSONString(req);
        System.out.println("req json: "+reqJson);
        OkHttpClient client = getClient(url);
        if(Objects.isNull(client)){
            client  = new OkHttpClient();
            cacheClient(url,client);
        }
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSONTYPE, reqJson))
                .build();
        String respJson = client.newCall(request).execute().body().string();
        System.out.println("resp json: "+respJson);
        return JSON.parseObject(respJson, RpcfxResponse.class);
    }
    void cacheClient(String url,OkHttpClient client){
        URI uri = URI.create(url);
        String host = uri.getHost();
        int port =  uri.getPort();
        clients.put(host.concat(":"+port),client);
    }
    OkHttpClient getClient(String url){
        URI uri = URI.create(url);
        String host = uri.getHost();
        int port =  uri.getPort();
        return clients.get(host.concat(":"+port));
    }
}
