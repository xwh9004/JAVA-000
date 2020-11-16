package com.example.spring.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class Student implements Serializable {

    
    private int id;
    private String name;

    public void init(){
        this.id= IDCounter.generateId();
        this.name="K00"+id;
        System.out.println("hello...........");
    }


    public static Student create(){
          int id = IDCounter.generateId();
         return new Student(id,"K00"+id);
    }

    static class IDCounter {
        private static AtomicInteger count =new AtomicInteger();

        static int generateId(){
           return count.incrementAndGet();
        }

    }

}
