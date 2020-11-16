package com.example.aop;

import lombok.Data;

@Data
public class School implements ISchool {


    @Override
    public void ding() {
        System.out.println("dingding.... !");
    }

    static void dang(){
        System.out.println("dangdang.... !");
    }
}
