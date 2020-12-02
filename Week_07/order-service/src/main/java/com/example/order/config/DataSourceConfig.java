package com.example.order.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

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
public class DataSourceConfig {


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
    public JdbcTemplate jdbcTemplate(@Qualifier("shardingSphereDataSource") DataSource dataSource){
        //先配置默认数据源
        return new JdbcTemplate(dataSource);
    }



}
