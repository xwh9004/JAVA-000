package com.example.order;

import com.example.order.entity.Order;
import com.example.order.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication(scanBasePackageClasses =OrderServiceApplication.class )
public class OrderServiceApplication {

    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext application = SpringApplication.run(OrderServiceApplication.class);


        OrderService orderService = application.getBean(OrderService.class);

        for(int i=0;i<5;i++){
            System.out.println(orderService.query(2));
        }
//        Order order =new Order();
//        order.setUserId(3);
//        order.setProductId(1);
//        order.setProductAmount(2);
//        order.setProductUnitPrice(88.90);
//        order.setOrderTotalPrice(88.90*order.getProductAmount());
//        orderService.save(order);
        application.stop();

    }
}
