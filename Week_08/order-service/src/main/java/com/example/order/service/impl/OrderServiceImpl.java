package com.example.order.service.impl;

import com.example.order.annotatiion.DataSourceTarget;
import com.example.order.entity.Order;
import com.example.order.service.OrderService;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.driver.jdbc.core.datasource.ShardingSphereDataSource;
import org.apache.shardingsphere.driver.jdbc.core.resultset.ShardingSphereResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static com.example.order.sql.SqlStatements.INSERT_SQL;
import static com.example.order.sql.SqlStatements.SELECT_SQL;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 16:05 on 2020/12/1
 * @version V0.1
 * @classNmae OrderServiceImpl
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

//    @Autowired
//    private DynamicRoutingDataSource dynamicDataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;


//    @Qualifier("primaryJdbcTemplate")
//    @Autowired
//    private JdbcTemplate primaryJdbcTemplate;
//
//    @Qualifier("replicationJdbcTemplate")
//    @Autowired
//    private JdbcTemplate replicationJdbcTemplate;

    @DataSourceTarget(name = "primaryDataSource")
    public void save(Order order) {

        //insert into t_order
        // (order_no,product_id,product_amount,
        // product_unit_price,order_total_price, user_id,
        // status,order_desc,create_time,update_time)
        // values(?,?,?,
        // ?,?,?,
        // 0,null,?,?)
//        primaryJdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                PreparedStatement prepareStatement = connection.prepareStatement(INSERT_SQL);
//                order.setNo(UUID.randomUUID().toString());
//                prepareStatement.setString(1,order.getNo());
//                prepareStatement.setInt(2,order.getProductId());
//                prepareStatement.setDouble(3,order.getProductAmount());
//                prepareStatement.setDouble(4,order.getProductUnitPrice());
//                prepareStatement.setDouble(5,order.getOrderTotalPrice());
//                prepareStatement.setInt(6,order.getUserId());
//                long createTime = System.currentTimeMillis();
//                order.setCreateTime(createTime);
//                order.setUpdateTime(createTime);
//
//                prepareStatement.setLong(7,order.getCreateTime());
//                prepareStatement.setLong(8,order.getUpdateTime());
//                return prepareStatement;
//            }
//        });
//        jdbcTemplate.setDataSource(dynamicDataSource.getDataSource());
        log.info("从数据库写入数据 url:"+((HikariDataSource)jdbcTemplate.getDataSource()).getJdbcUrl());
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement prepareStatement = connection.prepareStatement(INSERT_SQL);
                order.setNo(UUID.randomUUID().toString());
                prepareStatement.setString(1,order.getNo());
                prepareStatement.setInt(2,order.getProductId());
                prepareStatement.setDouble(3,order.getProductAmount());
                prepareStatement.setDouble(4,order.getProductUnitPrice());
                prepareStatement.setDouble(5,order.getOrderTotalPrice());
                prepareStatement.setInt(6,order.getUserId());
                long createTime = System.currentTimeMillis();
                order.setCreateTime(createTime);
                order.setUpdateTime(createTime);

                prepareStatement.setLong(7,order.getCreateTime());
                prepareStatement.setLong(8,order.getUpdateTime());
                return prepareStatement;
            }
        });
    }

    @DataSourceTarget(readOnly = true)
    public Order query(Integer orderId) {
        Order order =new Order();

//        replicationJdbcTemplate.query(new PreparedStatementCreator() {
//
//            @Override
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                PreparedStatement prepareStatement = connection.prepareStatement(SELECT_SQL);
//
//                prepareStatement.setInt(1, orderId);
//
//                return prepareStatement;
//            }
//        }, new RowCallbackHandler() {
//            @Override
//            public void processRow(ResultSet resultSet) throws SQLException {
//                if(resultSet.first()){
//                    order.setId(resultSet.getInt("order_id"));
//                    order.setNo(resultSet.getString("order_no"));
//                    order.setProductAmount(resultSet.getInt("product_amount"));
//
//                }
//            }
//        });
//        jdbcTemplate.setDataSource(dynamicDataSource.getDataSource());

//        log.info("从数据库查询数据 url:"+((HikariDataSource)jdbcTemplate.getDataSource()).getJdbcUrl());


        log.info("从数据库查询数据 url:"+((ShardingSphereDataSource)jdbcTemplate.getDataSource()).toString());
        jdbcTemplate.query(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement prepareStatement = connection.prepareStatement(SELECT_SQL);

                prepareStatement.setInt(1, orderId);

                return prepareStatement;
            }
        }, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet0) throws SQLException {
                ResultSet resultSet = resultSet0;
                if(resultSet0 instanceof  ShardingSphereResultSet ){
                    ShardingSphereResultSet shardingResult = (ShardingSphereResultSet)resultSet0;
                    resultSet = shardingResult.getResultSets().get(0);
                }
                    order.setId(resultSet.getInt("order_id"));
                    order.setNo(resultSet.getString("order_no"));
                    order.setProductAmount(resultSet.getInt("product_amount"));
                    order.setProductId(resultSet.getInt("product_id"));
                    order.setUserId(resultSet.getInt("user_id"));
                    order.setOrderTotalPrice(resultSet.getDouble("order_total_price"));
                    order.setProductUnitPrice(resultSet.getDouble("product_unit_price"));
                    order.setCreateTime(resultSet.getLong("create_time"));
                    order.setUpdateTime(resultSet.getLong("update_time"));

            }
        });
        return order;
    }
}
