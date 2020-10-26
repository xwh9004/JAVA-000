package com.example.train.week2;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 17:12 on 2020/10/26
 * @version V0.1
 * @classNmae MyGateWay
 */
public class MyGateWay {

    public static void main(String[] args) {

        HttpClient client = HttpClients.createDefault();
        HttpUriRequest uri = new HttpGet();
      
        try {
            ((HttpGet) uri).setURI(new URI("http://localhost:8801/"));
            HttpResponse response = client.execute(uri);
            HttpEntity responseEntity = response.getEntity();
            InputStream content = responseEntity.getContent();
            byte[] buf=new byte[1024];
            int len = content.read(buf);
            String rest = new String(buf,0,len);
            System.out.println(rest);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
