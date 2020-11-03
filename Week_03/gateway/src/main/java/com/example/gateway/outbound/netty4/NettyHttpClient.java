package com.example.gateway.outbound.netty4;//package io.github.kimmking.com.example.gateway.outbound;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyHttpClient {

    private FullHttpRequest request;

    private ChannelHandlerContext serverCxt;

    public void setRequest(FullHttpRequest request) {
        this.request = request;
    }


    public void setServerCxt(ChannelHandlerContext serverCxt) {
        this.serverCxt = serverCxt;
    }

    public void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
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

                    NettyHttpClientOutboundHandler clientOutboundHandler  =  new NettyHttpClientOutboundHandler();
                    clientOutboundHandler.setFullRequest(request);
                    clientOutboundHandler.setServerCtx(serverCxt);
                    ch.pipeline().addLast(clientOutboundHandler);
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().write(request);
            f.channel().flush();

            f.channel().closeFuture().sync();


        } finally {
            workerGroup.shutdownGracefully().sync();
            log.info("NettyHttpClient shutdownGracefully");
        }

    }
}