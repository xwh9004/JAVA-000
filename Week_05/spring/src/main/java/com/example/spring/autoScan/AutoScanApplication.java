package com.example.spring.autoScan;

import com.example.spring.bean.HighSchool;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

/**
 * <p><b>Description:</b>
 *
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 11:10 on 2020/11/16
 * @version V0.1
 * @classNmae AutoScanApplication
 */

@Configuration
@ComponentScan(basePackages = {"com.example.spring.autoScan","com.example.spring.bean"})
public class AutoScanApplication {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutoScanApplication.class);

        HighSchool highSchool = applicationContext.getBean(HighSchool.class);

        System.out.println(highSchool);
    }

}
