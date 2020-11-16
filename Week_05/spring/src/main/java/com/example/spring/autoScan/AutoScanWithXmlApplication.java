package com.example.spring.autoScan;

import com.example.spring.bean.College;
import com.example.spring.bean.HighSchool;
import org.springframework.context.annotation.*;

/**
 * <p><b>Description:</b>
 * Xml与Annotation结合方式
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 11:10 on 2020/11/16
 * @version V0.1
 * @classNmae AutoScanApplication
 */
@ImportResource("META-INF/bean-context.xml")
@Configuration
@ComponentScan(basePackages = {"com.example.spring.autoScan","com.example.spring.bean"})
public class AutoScanWithXmlApplication {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutoScanWithXmlApplication.class);

        College college = applicationContext.getBean(College.class);

        System.out.println(college);
    }

}
