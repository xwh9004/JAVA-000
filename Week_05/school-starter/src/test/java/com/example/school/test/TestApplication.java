package com.example.school.test;

import com.example.autoconfig.bean.ISchool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 14:48 on 2020/11/17
 * @version V0.1
 * @classNmae TestApplication
 */
//@PropertySource("/META-INF/application.properties")
@EnableConfigurationProperties
public class TestApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(TestApplication.class);
        ISchool school = application.getBean(ISchool.class);
        school.ding();
    }
}
