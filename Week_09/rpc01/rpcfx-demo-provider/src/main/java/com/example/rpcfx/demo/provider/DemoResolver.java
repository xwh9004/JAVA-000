package com.example.rpcfx.demo.provider;

import com.example.rpcfx.api.RpcfxResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DemoResolver implements RpcfxResolver, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <T> T resolve(String serviceClass) throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName(serviceClass);
            return (T)this.applicationContext.getBean(cls);
        } catch (ClassNotFoundException e) {
            throw e;
        }


    }
}
