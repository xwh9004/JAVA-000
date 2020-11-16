package com.example.aop.asm;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 17:44 on 2020/11/16
 * @version V0.1
 * @classNmae Advice
 */
public class Advice {

    public static void before() {
        System.out.println("上课了。。。");
    }
    public static void after() {
        System.out.println("下课了。。。");
    }
}
