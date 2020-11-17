package com.example.springboot;


import com.example.springboot.entity.User;
import com.example.springboot.service.IUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 16:30 on 2020/11/17
 * @version V0.1
 * @classNmae JdbcApplication
 */

@SpringBootApplication(scanBasePackageClasses =JdbcApplication.class )
public class JdbcApplication {

    public static void main(String[] args) throws SQLException {
        jdbcOpt();

    }

    /**
     * 默认使用Hikari线程池操作
     */
    public static void jdbcOpt(){
        ConfigurableApplicationContext context = SpringApplication.run(JdbcApplication.class);
        IUserService service = context.getBean(IUserService.class);
       System.out.println(service.select(1));
//        service.insert(new User(2,"Jesse1","Xu"));
//        service.update(new User(2,"Jesse122","Xu"));
//        service.delete(2);

//        List<User> users = new ArrayList<>();
//        users.add(new User(3,"Jesse03","Xu03"));
//        users.add(new User(4,"Jesse04","Xu04"));
//        users.add(new User(5,"Jesse05","Xu05"));
//        service.batchInsert(users);
    }





}
