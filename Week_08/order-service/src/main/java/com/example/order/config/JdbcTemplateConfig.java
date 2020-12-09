package com.example.order.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 14:18 on 2020/12/2
 * @version V0.1
 * @classNmae JdbcTemplateConfig
 */
//@Configuration
public class JdbcTemplateConfig {

    @Bean("primaryJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean("replicationJdbcTemplate")
    public JdbcTemplate replicationJdbcTemplate1(@Qualifier("replicationDataSource1") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean("replicationJdbcTemplate")
    public JdbcTemplate replicationJdbcTemplate2(@Qualifier("replicationDataSource2") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
