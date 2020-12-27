package com.example.rpcfx.client.nettyClient;

import com.alibaba.fastjson.JSON;
import com.example.rpcfx.api.RpcfxRequest;
import com.example.rpcfx.api.RpcfxResponse;
import com.example.rpcfx.client.Client;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Slf4j
public class RpcNettyClient implements Client {

    private EventLoopGroup workerGroup;

    private Bootstrap b = null;

    private  Map<String, ChannelFuture> channelMap = new ConcurrentHashMap<String,ChannelFuture>();

    private Map<String, ResultFuture> respMap = new HashMap<>();
    private NettyClientInboundHandler outboundHandler = new NettyClientInboundHandler();

    private ExecutorService executorService = Executors.newFixedThreadPool(5);



    public RpcNettyClient(){
        init();
    }

    @Override
    public RpcfxResponse invoke(RpcfxRequest req, String url) throws IOException {
        req.setMsgId(UUID.randomUUID().toString());
        String reqJson = JSON.toJSONString(req);
        System.out.println("req json: "+reqJson);
        URI uri = URI.create(url);
        String host = uri.getHost();
        int port =  uri.getPort();
        FullHttpRequest fullRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.POST,url, Unpooled.wrappedBuffer(reqJson.getBytes()));
        fullRequest.headers().set("accept-type", Charsets.UTF_8);
        fullRequest.headers().set(HttpHeaderNames.HOST, host);
        fullRequest.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        fullRequest.headers().set(HttpHeaderNames.CONTENT_LENGTH, fullRequest.content().readableBytes());
        fullRequest.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        respMap.put(req.getMsgId(),new ResultFuture());
        executorService.submit(() -> {
            try {
                ChannelFuture channelFuture = connect(host, port);
                channelFuture.channel().writeAndFlush(fullRequest).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        System.out.println("operationComplete!");
                    }
                });
                close(channelFuture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        RpcfxResponse response = null;
        try {
             response =  respMap.get(req.getMsgId()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void init() {
        outboundHandler.setRespMap(respMap);
        workerGroup = new NioEventLoopGroup();
        b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
                        .addLast(new HttpResponseDecoder())   // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                        .addLast(new HttpRequestEncoder()) // 客户端发送的是httpRequest，所以要使用HttpRequestEncoder进行编码
                        .addLast(new HttpObjectAggregator(1024 * 10 * 1024));

                ch.pipeline().addLast(outboundHandler);

            }
        });

    }

    public void close(ChannelFuture f) throws InterruptedException {
        try {
            f.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully().sync();
            log.info("NettyHttpClient shutdownGracefully");
        }
    }

    public ChannelFuture connect(String host, int port) throws Exception {
        // Start the client.
        String remoteHost = host+":"+port;
        ChannelFuture channelFuture= channelMap.get(remoteHost);
        if(channelFuture==null){
            channelFuture = b.connect(host,port).sync();
            channelMap.put(remoteHost,channelFuture);
        }
        return channelFuture;
    }
}
