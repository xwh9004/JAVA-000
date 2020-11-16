package com.example.spring.bean;


import com.example.spring.interfaces.ISchool;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class HighSchool implements ISchool {

    @Autowired
    private Klass class1;

    @Autowired
    private Student student100;

    @Override
    public void ding(){

        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);

    }
}
