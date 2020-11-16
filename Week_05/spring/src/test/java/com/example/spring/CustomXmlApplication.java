package com.example.spring;

import com.example.spring.bean.Klass;
import com.example.spring.bean.Student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 9:28 on 2020/11/16
 * @version V0.1
 * @classNmae XmlInjectApplication
 */
public class CustomXmlApplication {




    /**
     * Xml配置bean 测试代码
     */
    @Test
    public  void getBeanByType(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/student-context.xml");

        Student student1 = applicationContext.getBean(Student.class);

        System.out.println(student1);

    }
}
