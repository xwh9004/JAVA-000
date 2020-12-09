package com.example.order.test;

import com.example.order.OrderServiceApplication;
import com.example.order.entity.Order;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import static com.example.order.sql.SqlStatements.INSERT_SQL;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 17:09 on 2020/12/1
 * @version V0.1
 * @classNmae TestApplication
 */
public class TestApplication {

    @Test
    public void insert_test(){

        int[] users = new int[]{1,2,3,4,5,6,7,8,9,10};
        int[] products = new int[]{1,2,3,4,5,6,7,8,9,10};
        double[] products_price = new double[]{1.1,2.2,3.3,4.3,5.6,6.6,7.9,8.1,9.0,10.1};

        ConfigurableApplicationContext application = SpringApplication.run(OrderServiceApplication.class);

        JdbcTemplate jdbcTemplate =   application.getBean(JdbcTemplate.class);

        Order order = new Order();
        order.setUserId(users[3]);;
        order.setNo(UUID.randomUUID().toString().replace("-",""));

        order.setProductAmount(1);
        order.setProductId(products[2]);
        order.setProductUnitPrice(products_price[order.getProductId()]);
        order.setOrderTotalPrice(order.getProductUnitPrice()*order.getProductAmount());
        System.out.printf("插入数据 order no hashCode=%s  ,rout to order_db_%d , t_order_%d \n",order.getNo().hashCode(),Math.abs(order.getNo().hashCode()) % 2,order.getUserId());


        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement prepareStatement = connection.prepareStatement(INSERT_SQL);

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

        System.out.printf("插入数据完成 ");
    }
}
