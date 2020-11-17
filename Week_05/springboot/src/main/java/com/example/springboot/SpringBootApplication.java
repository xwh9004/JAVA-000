package com.example.springboot;

import com.example.autoconfig.bean.ISchool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 10:09 on 2020/11/17
 * @version V0.1
 * @classNmae SpringBootApplication
 */
@EnableAutoConfiguration
public class SpringBootApplication {


    public static void main(String[] args) {
        autoConfigSchool();


    }


    /**
     * 实现 School类的自动装配
     * 1 在autoconfig_school项目中，配置School
     * 2 在autoconfig_school项目META-INF 目录下创建spring.factories文件指定 自动装配的配置类
     * 3 在引用的项目中 @EnableAutoConfiguration 启动自动装配
     */
    public static void autoConfigSchool(){
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootApplication.class);

        ISchool school = context.getBean(ISchool.class);

        school.ding();
    }

}
