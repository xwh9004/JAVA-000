package com.example.train;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 16:36 on 2020/10/16
 * @version V0.1
 * @classNmae HelloClassLoader
 */
public class HelloClassLoader extends ClassLoader{

    public static void main(String[] args) {
        String xlass = "Hello";
        HelloClassLoader classLoader = new HelloClassLoader();
        try {
            Object hello = classLoader.findClass(xlass).newInstance();
            Method method = hello.getClass().getMethod("hello");
            method.invoke(hello);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes =null;
        int len = 0;
        try {
            URL url = getResource(name+".xlass");
            File file = new File(url.getFile());
            FileInputStream in = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int length = in.read(buffer);
            bytes = new byte[length];
            for(int i=0;i<length;i++){
                bytes[i] = (byte)(255-buffer[i]);
                // bytes[i] = buffer[i];
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name,bytes,0,bytes.length);
    }
}
