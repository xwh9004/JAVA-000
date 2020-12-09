package com.example.order.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 9:45 on 2020/12/2
 * @version V0.1
 * @classNmae DynamicRoutingDataSource
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private ThreadLocal<String> dataSourceKeys = new ThreadLocal<String>();


    public DataSource getDataSource(){
        return determineTargetDataSource();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceKeys.get();
    }

    public void setRoutKey(String key){

        dataSourceKeys.set(key);
    }



    public void clearRoutKey(){
        dataSourceKeys.remove();
    }
}
