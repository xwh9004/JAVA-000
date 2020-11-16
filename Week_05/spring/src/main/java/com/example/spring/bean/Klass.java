package com.example.spring.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Klass {

    private String klassName;
    
    List<Student> students;
    
    public void dong(){
        System.out.println(this.getStudents());
    }
    
}
