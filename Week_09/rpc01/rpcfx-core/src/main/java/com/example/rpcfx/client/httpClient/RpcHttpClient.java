package com.example.rpcfx.client.httpClient;

import com.alibaba.fastjson.JSON;
import com.example.rpcfx.api.RpcfxRequest;
import com.example.rpcfx.api.RpcfxResponse;
import com.example.rpcfx.client.Client;
import okhttp3.OkHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RpcHttpClient implements Client {

    private static Map<String ,HttpClient> clients = new HashMap<>();
    @Override
    public RpcfxResponse invoke(RpcfxRequest req, String url) throws IOException {

        String reqJson = JSON.toJSONString(req);
        System.out.println("req json: "+reqJson);
        HttpClient client = getClient(url);
        if(Objects.isNull(client)){
            client = HttpClientBuilder.create().build();
            cacheClient(url,client);
        }
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        StringEntity httpEntity = new StringEntity(reqJson);
        httpPost.setEntity(httpEntity);

        HttpResponse httpResponse = client.execute(httpPost);
        HttpEntity responseEnitiy = httpResponse.getEntity();
        byte[] contents = new byte[1024];
        int len = responseEnitiy.getContent().read(contents);
        String respJson = new String(contents,0,len);
        System.out.println("resp json: "+respJson);
        return JSON.parseObject(respJson, RpcfxResponse.class);
    }

    void cacheClient(String url, HttpClient client){
        URI uri = URI.create(url);
        String host = uri.getHost();
        int port =  uri.getPort();
        clients.put(host.concat(":"+port),client);
    }
    HttpClient getClient(String url){
        URI uri = URI.create(url);
        String host = uri.getHost();
        int port =  uri.getPort();
        return clients.get(host.concat(":"+port));
    }
}
