package com.example.order.config;

import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.RuleConfiguration;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.replicaquery.api.config.ReplicaQueryRuleConfiguration;
import org.apache.shardingsphere.replicaquery.api.config.rule.ReplicaQueryDataSourceRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
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

    @Bean
    public DataSource shardingSphereDataSource() throws SQLException {

        Map<String, DataSource> dataSourcesLoad = context.getBeansOfType(DataSource.class);


        Map<String, DataSource> dataSources = new HashMap<>();

        dataSources.put("order_db_0",dataSourcesLoad.get("primaryDataSource"));
        dataSources.put("order_db_1",dataSourcesLoad.get("primaryDataSource1"));


        ReplicaQueryRuleConfiguration replicaRuleConfiguration = replicaQueryRuleConfiguration();
        ShardingRuleConfiguration ruleConfiguration=shardingRuleConfiguration();

        Collection<RuleConfiguration> configurations = Arrays.asList(ruleConfiguration,replicaRuleConfiguration);
        Properties properties = new Properties();
        return ShardingSphereDataSourceFactory.createDataSource(dataSources,configurations,properties);

    }


    /**
     * shardingSphere sharding rule configuration
     * @return
     */
    private ShardingRuleConfiguration shardingRuleConfiguration(){
        // Configure order table rule
        ShardingTableRuleConfiguration orderTableRuleConfig =
                new ShardingTableRuleConfiguration("t_order", "order_db_${0..1}.t_order_${0..15}");

        // Configure database sharding strategy
        StandardShardingStrategyConfiguration databaseShardingStrategyConfiguration =
                new StandardShardingStrategyConfiguration("order_no", "dbShardingAlgorithm");
        orderTableRuleConfig.setDatabaseShardingStrategy(databaseShardingStrategyConfiguration);


        // Configure table sharding strategy
        StandardShardingStrategyConfiguration tableShardingStrategyConfiguration =
                new StandardShardingStrategyConfiguration("user_id", "tableShardingAlgorithm");
        orderTableRuleConfig.setTableShardingStrategy(tableShardingStrategyConfiguration);

        // Configure sharding rule
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTables().add(orderTableRuleConfig);

        // Configure database sharding algorithm
        Properties dbShardingAlgorithmProps = new Properties();
        dbShardingAlgorithmProps.setProperty("algorithm-expression", "order_db_${Math.abs(order_no.hashCode()) % 2}");
        shardingRuleConfig.getShardingAlgorithms().put("dbShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", dbShardingAlgorithmProps));

        // Configure table sharding algorithm
        Properties tableShardingAlgorithmProps = new Properties();
        tableShardingAlgorithmProps.setProperty("algorithm-expression", "t_order_${user_id % 16}");
        shardingRuleConfig.getShardingAlgorithms().put("tableShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", tableShardingAlgorithmProps));


        return shardingRuleConfig;
    }

    /**
     * shardingSphere replica query rule configuration
     * @return
     */
    private ReplicaQueryRuleConfiguration replicaQueryRuleConfiguration() {
        ReplicaQueryDataSourceRuleConfiguration dataSourceRuleConfig =
                new ReplicaQueryDataSourceRuleConfiguration( "ds_0",
                        "primaryDataSource",
                        Arrays.asList("replicationDataSource1", "replicationDataSource2"), "round_robin"
        );
        return new ReplicaQueryRuleConfiguration(Arrays.asList(dataSourceRuleConfig), Collections.emptyMap());
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
