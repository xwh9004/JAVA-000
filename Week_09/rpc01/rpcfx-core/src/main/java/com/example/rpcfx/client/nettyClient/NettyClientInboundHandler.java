package com.example.rpcfx.client.nettyClient;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
@Slf4j
@ChannelHandler.Sharable
public class NettyClientInboundHandler extends ChannelInboundHandlerAdapter {
    private String res = null;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            try {
                byte[] contents = new byte[1024];
                int len =  response.content().readableBytes();
                response.content().readBytes(contents,0,len);
                res = new String(contents,0,len);
//                System.out.println(res);
                ctx.writeAndFlush(msg);
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().close();
    }

    public String getRes() {
        return res;
    }
}
