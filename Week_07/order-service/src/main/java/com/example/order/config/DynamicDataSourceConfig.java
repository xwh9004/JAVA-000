package com.example.order.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 14:15 on 2020/12/2
 * @version V0.1
 * @classNmae DynamicDataSourceConfig
 */
//@Configuration
public class DynamicDataSourceConfig  implements ApplicationContextAware {

    private ApplicationContext context;

    @Bean
    public DynamicRoutingDataSource dynamicRoutingDataSource(){
        DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource();

        Map<String, DataSource> dataSources = context.getBeansOfType(DataSource.class);

        Map<Object, Object> routingDataSources =new HashMap<>();

        routingDataSources.putAll(dataSources);
        routingDataSource.setTargetDataSources(routingDataSources);
        routingDataSource.afterPropertiesSet();
        return routingDataSource;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
