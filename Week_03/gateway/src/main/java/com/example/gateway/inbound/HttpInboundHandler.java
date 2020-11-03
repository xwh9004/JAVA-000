package com.example.gateway.inbound;

import com.example.gateway.filter.HttpRequestFilter;
import com.example.gateway.filter.HttpRequestTraceFilter;
import com.example.gateway.outbound.httpclient4.HttpOutboundHandler;
import com.example.gateway.outbound.netty4.NettyHttpClient;
import com.example.gateway.router.RandomHttpEndpointRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private HttpOutboundHandler handler;

    private NettyHttpClient client;

    private List<HttpRequestFilter> filters = new ArrayList<HttpRequestFilter>();
    
    public HttpInboundHandler() {

        handler = new HttpOutboundHandler();
        client = new NettyHttpClient();
        filters.add(new HttpRequestTraceFilter());
    }


    public void addFilter(HttpRequestFilter filter){
        filters.add(filter);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        //可以启动一个代理客户端
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            long startTime = System.currentTimeMillis();
            log.info("channelRead流量接口请求开始，时间为{}", startTime);
            // http://localhost:8888/api/{serviceName}/xxx
            final FullHttpRequest fullRequest = (FullHttpRequest) msg;
            String url = fullRequest.uri();
            if(url.startsWith("/api")){
                //filter process
                filters.stream().forEach(filter -> filter.filter(fullRequest,ctx));
                RandomHttpEndpointRouter router = new RandomHttpEndpointRouter();
                String backendUri =router.route(url);
//                httpClientHandler(backendUri,fullRequest,ctx);
                nettyClientHandler(backendUri,fullRequest,ctx);

            }
    
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }


    private void httpClientHandler(String uri,FullHttpRequest fullRequest,ChannelHandlerContext ctx){
        handler.setBackendUrl(uri);
        handler.handle(fullRequest, ctx);
        log.info("httpClientHandler done!");
    }

    private void nettyClientHandler(String uriStr,FullHttpRequest fullRequest,ChannelHandlerContext ctx) throws Exception{
        fullRequest =ReferenceCountUtil.retain(fullRequest);
        URI uri = new URI(uriStr);
        fullRequest.setUri(uriStr);
        client.setRequest(fullRequest);
        client.setServerCxt(ctx);
        client.connect(uri.getHost(),uri.getPort());
        log.info("nettyClientHandler done!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }


}
