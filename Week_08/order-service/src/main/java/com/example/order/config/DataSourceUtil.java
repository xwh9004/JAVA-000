package com.example.order.config;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 15:21 on 2020/12/2
 * @version V0.1
 * @classNmae DataSourceUtil
 */
public class DataSourceUtil {

    private static final String HOST = "localhost";

    private static final int PORT = 3306;

    private static final String USER_NAME = "root";

    private static final String PASSWORD = "";

    private static final String driveClassName ="com.mysql.cj.jdbc.Driver";

    public static DataSource createDataSource(final String dataSourceName) {
        return createDataSource(HOST,PORT,dataSourceName);
    }

    public static DataSource createDataSource(int port,final String dataSourceName) {
        return createDataSource(HOST,port,dataSourceName);
    }

    private static DataSource createDataSource(String host,int port,String dbName){
        HikariDataSource result = new HikariDataSource();
        result.setDriverClassName(driveClassName);
        result.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8", host, port, dbName));
        result.setUsername(USER_NAME);
        result.setPassword(PASSWORD);
        return result;
    }
}
