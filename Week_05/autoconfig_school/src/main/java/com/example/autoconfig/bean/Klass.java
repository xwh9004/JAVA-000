package com.example.autoconfig.bean;

import lombok.Data;

import java.util.List;

@Data
public class Klass {

    String klassName;
    
    List<Student> students;
    
    public void dong(){
        System.out.println(this.getStudents());
    }
    
}
