package com.example.spring.bean;


import com.example.spring.interfaces.ISchool;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p><b>Description:</b>
 *
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 9:46 on 2020/11/16
 * @version V0.1
 * @classNmae HigSchool
 */
@Component
@Data
public class College implements ISchool {


    @Resource(name = "klass")
    private Klass klass;

    @Resource(name = "student1")
    private Student monitor;

    @Override
    public void ding(){

        System.out.println("Class1 have " + this.klass.getStudents().size() + " class monitor and one is " + this.monitor);

    }
}
