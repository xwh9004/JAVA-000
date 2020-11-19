package com.example.aop.instrument;

import com.example.aop.ISchool;
import com.example.aop.School;

import java.lang.instrument.Instrumentation;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 14:20 on 2020/11/18
 * @version V0.1
 * @classNmae InstrumentApplication
 */
public class InstrumentSchoolProxy  {

    public static void main(String[] args) throws Exception {
        System.out.println("main run in " +Thread.currentThread().getName());
        ISchool school = new School();
        school.ding();
    }
}
