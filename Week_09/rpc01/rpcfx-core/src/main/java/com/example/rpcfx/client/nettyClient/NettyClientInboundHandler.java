package com.example.rpcfx.client.nettyClient;

import com.alibaba.fastjson.JSON;
import com.example.rpcfx.api.RpcfxResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


@Slf4j
@ChannelHandler.Sharable
public class NettyClientInboundHandler extends ChannelInboundHandlerAdapter {
    private Map<String, ResultFuture> respMap;

    public void setRespMap(Map<String, ResultFuture> respMap) {
        this.respMap = respMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpResponse) {
            FullHttpResponse fullHttpResponse = (FullHttpResponse) msg;
            try {
                byte[] contents = new byte[1024];

                int len =  fullHttpResponse.content().readableBytes();
                fullHttpResponse.content().readBytes(contents,0,len);
                String  respJson = new String(contents,0,len);
                RpcfxResponse response=JSON.parseObject(respJson, RpcfxResponse.class);
                respMap.get(response.getMsgId()).setSuccess(response);
                ctx.writeAndFlush(msg);
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }

}
