package com.example.spring;

import com.example.spring.bean.Klass;
import com.example.spring.bean.Student;
import com.example.spring.config.SchoolConfiguration;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 9:28 on 2020/11/16
 * @version V0.1
 * @classNmae XmlInjectApplication
 */
public class ConfigurationInjectApplication {


    /**
     * Xml配置bean 测试代码
     */
    @Test
    public  void getBeanByType(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(SchoolConfiguration.class);
        applicationContext.refresh();
        Student student1 = applicationContext.getBean(Student.class);

        System.out.println(student1);

        Klass klass = applicationContext.getBean(Klass.class);

        System.out.println(klass);
    }
}
