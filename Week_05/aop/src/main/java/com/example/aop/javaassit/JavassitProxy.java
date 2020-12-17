package com.example.aop.javaassit;

import com.example.aop.School;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.util.HotSwapper;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 14:46 on 2020/12/17
 * @version V0.1
 * @classNmae JavassitProxy
 */
public class JavassitProxy {

    public static void main(String[] args) throws Exception {

//        School.dang("","");

        ClassPool pool = ClassPool.getDefault();

        CtClass ctClass = pool.get(School.class.getName());


        // 获取方法
        CtMethod ctMethod = ctClass.getDeclaredMethod("dang");

        ctMethod.insertBefore("System.out.println(\"before..\"+ $1 + $2);");

        ctMethod.insertAfter("System.out.println(\"after....\" + \" \"+$_);");


        // 监听 8000 端口,在启动参数里设置
        // java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000
        HotSwapper hs = new HotSwapper(8000);

        hs.reload(School.class.getName(), ctClass.toBytecode());

        School.dang("hekko","ddd");
    }
}
