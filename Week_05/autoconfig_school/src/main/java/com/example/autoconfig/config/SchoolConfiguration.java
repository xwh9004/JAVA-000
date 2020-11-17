package com.example.autoconfig.config;

import com.example.autoconfig.bean.ISchool;
import com.example.autoconfig.bean.School;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 9:32 on 2020/11/17
 * @version V0.1
 * @classNmae SchoolConfiguration
 */
@ImportResource("META-INF/school-context.xml")
@Configuration
public class SchoolConfiguration {

}
