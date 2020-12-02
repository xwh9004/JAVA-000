package com.example.order.config;

import com.example.order.db.DynamicRoutingDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 16:22 on 2020/12/1
 * @version V0.1
 * @classNmae DataSourceConfig
 */
@Configuration
public class DataSourceConfig implements ApplicationContextAware {

    private ApplicationContext context;

    @Bean("primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource(){
        return DataSourceBuilder.create().build();
    }


    @Bean("replicationDataSource1")
    @ConfigurationProperties(prefix = "spring.datasource.replication1")
    public DataSource replicationDataSource1(){
        return DataSourceBuilder.create().build();
    }

    @Bean("replicationDataSource2")
    @ConfigurationProperties(prefix = "spring.datasource.replication2")
    public DataSource replicationDataSource2(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){

        //先配置默认数据源
        return new JdbcTemplate(primaryDataSource());
    }
//    @Bean("primaryJdbcTemplate")
//    public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource){
//        return new JdbcTemplate(dataSource);
//    }
//
//    @Bean("replicationJdbcTemplate")
//    public JdbcTemplate replicationJdbcTemplate(@Qualifier("replicationDataSource") DataSource dataSource){
//        return new JdbcTemplate(dataSource);
//    }
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
