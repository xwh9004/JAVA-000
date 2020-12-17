package com.example.aop;

import lombok.Data;

@Data
public class School implements ISchool {


    @Override
    public void ding() {
        System.out.println("dingding.... !");
    }

    public static String dang(String sss,String hh){
        System.out.println("dangdang.... !");
        return sss.concat(hh);
    }
}
