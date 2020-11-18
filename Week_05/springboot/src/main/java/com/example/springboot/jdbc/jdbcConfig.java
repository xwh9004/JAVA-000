package com.example.springboot.jdbc;

/**
 * <p><b>Description:</b>
 * 自定义Connection Bean
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 17:46 on 2020/11/17
 * @version V0.1
 * @classNmae jdbcConfig
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
@Slf4j
@Configuration
public class jdbcConfig {

    @Bean
    public Connection connection() {
        // 数据库驱动类名的字符串
        String driver = "com.mysql.cj.jdbc.Driver";
        // 数据库连接串
        String url = "jdbc:mysql://118.31.122.126:3306/test2?useSSL=false&useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true&serverTimezone=GMT%2B8";
        // 用户名
        String username = "Jesse";
        // 密码
        String password = "Jesse@123456";

        ResultSet rs = null;
        Connection conn = null;
        try {
            // 1、加载数据库驱动（ 成功加载后，会将Driver类的实例注册到DriverManager类中）
            Class.forName(driver);

            // 2、获取数据库连接
             conn = DriverManager.getConnection(url, username, password);
             log.info("create jdbc connection successful!");

            // 3、获取数据库操作对象
        } catch (Exception e) {
            log.error("create jdbc connection failure!\n"+e.getMessage());
        }
        return conn;
    }
}