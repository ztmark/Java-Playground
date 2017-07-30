package com.mark.classload;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.mark.reflection.MyClassLoad;

/**
 * Author: Mark
 * Date  : 2017/7/30
 */
public class MyClassLoader extends ClassLoader {

    private String classLoaderPath;

    public MyClassLoader(String classLoaderPath) {
        if (!classLoaderPath.endsWith("/")) {
            classLoaderPath = classLoaderPath + "/";
        }
        this.classLoaderPath = classLoaderPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        final String pathName = name.replaceAll("\\.", "/");
        String fullClassPath = classLoaderPath + pathName + ".class";
        try (final BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fullClassPath))) {
            byte[] buffer = new byte[bufferedInputStream.available()];
            bufferedInputStream.read(buffer, 0, buffer.length);
            return defineClass(name, buffer, 0, buffer.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(fullClassPath + " is not exists");
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyClassLoader classLoad = new MyClassLoader("/Users/Mark");
        final Class<?> aClass = classLoad.loadClass("com.github.ztmark.Test");
        System.out.println(aClass.getClassLoader().getClass().getName());
        System.out.println(aClass);
        final Object o = aClass.newInstance();
        System.out.println(o);
        final Method hello = aClass.getMethod("hello");
        final Object invoke = hello.invoke(o);
        System.out.println(invoke);
    }

}
