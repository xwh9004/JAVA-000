package com.example.order.config;

import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.RuleConfiguration;
import org.apache.shardingsphere.replicaquery.api.config.ReplicaQueryRuleConfiguration;
import org.apache.shardingsphere.replicaquery.api.config.rule.ReplicaQueryDataSourceRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 14:11 on 2020/12/2
 * @version V0.1
 * @classNmae ShardingSphereConfig
 */

@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
public class ShardingSphereConfig implements ApplicationContextAware {

    private ApplicationContext context;
    @Autowired
    private DataSource primaryDataSource;

    @Autowired
    private DataSource replicationDataSource1;

    @Autowired
    private DataSource replicationDataSource2;

    @Bean
    public DataSource shardingSphereDataSource() throws SQLException {

        Map<String, DataSource> dataSources = context.getBeansOfType(DataSource.class);
//        Map<String, DataSource> dataSources = new HashMap<>();
//        dataSources.put("primaryDataSource",DataSourceUtil.createDataSource("db"));
//        dataSources.put("replicationDataSource1",DataSourceUtil.createDataSource(3316,"db"));
//        dataSources.put("replicationDataSource2",DataSourceUtil.createDataSource(3326,"db"));
        ReplicaQueryDataSourceRuleConfiguration dataSourceRuleConfig =
                new ReplicaQueryDataSourceRuleConfiguration( "ds_0",
                        "primaryDataSource",
                        Arrays.asList("replicationDataSource1", "replicationDataSource2"), "round_robin"
        );
        ReplicaQueryRuleConfiguration ruleConfiguration =
                new ReplicaQueryRuleConfiguration(Arrays.asList(dataSourceRuleConfig), Collections.emptyMap());


        Collection<RuleConfiguration> configurations = Arrays.asList(ruleConfiguration);
        Properties properties = new Properties();
        return ShardingSphereDataSourceFactory.createDataSource(dataSources,configurations,properties);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
