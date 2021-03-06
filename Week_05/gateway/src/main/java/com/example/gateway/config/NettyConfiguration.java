package com.example.gateway.config;

import com.example.gateway.outbound.Invoker;
import com.example.gateway.outbound.netty4.NettyClientInvoker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 17:22 on 2020/11/19
 * @version V0.1
 * @classNmae NettyConfiguration
 */
@Configuration
public class NettyConfiguration {

    @Bean
    public Invoker clientInvoker(){
        return new NettyClientInvoker();
    }
}
