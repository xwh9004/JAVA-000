package com.example.springboot;

import com.example.autoconfig.bean.ISchool;
import com.example.autoconfig.config.SchoolConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 10:09 on 2020/11/17
 * @version V0.1
 * @classNmae SpringBootApplication
 */
@PropertySource("/META-INF/application.properties")
@EnableConfigurationProperties
@SpringBootApplication(exclude = SchoolConfiguration.class)   //将schoolautoConfig包中的自动装配配排除
public class SpringBootStarterApplication {


    public static void main(String[] args) {
        autoConfigSchoolStarter();
    }


    /**
     * 实现 School类的自动装配
     * 1 在autoconfig_school项目中，配置School
     * 2 在autoconfig_school项目META-INF 目录下创建spring.factories文件指定 自动装配的配置类
     * 3 在引用的项目中 @EnableAutoConfiguration 启动自动装配
     */
    public static void autoConfigSchoolStarter(){
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootStarterApplication.class);

        ISchool school = context.getBean(ISchool.class);

        school.ding();
    }

}
