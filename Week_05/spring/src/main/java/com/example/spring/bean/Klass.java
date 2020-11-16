package com.example.spring.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
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
