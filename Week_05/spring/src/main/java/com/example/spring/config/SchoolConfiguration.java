package com.example.spring.config;


import com.example.spring.bean.HighSchool;
import com.example.spring.bean.Klass;
import com.example.spring.bean.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 10:12 on 2020/11/16
 * @version V0.1
 * @classNmae SchoolConfiguration
 */
@Configuration
public class SchoolConfiguration {


    @Bean(initMethod = "init")
    public Student student(){
        return Student.create();
    }
    @Bean
    public Klass klass(){
        Klass k = new Klass();
        k.setKlassName("高二(2)班");
        List<Student> students = new ArrayList<>();
        students.add(student());
        students.add(student());
        k.setStudents(students);
        return k;
    }
    @Bean
    public HighSchool highSchool(){
        HighSchool highSchool = new HighSchool();
        highSchool.setClass1(klass());
        highSchool.setStudent100(student());
        return highSchool;
    }

}
