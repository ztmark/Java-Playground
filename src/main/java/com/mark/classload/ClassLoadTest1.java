package com.mark.classload;

import java.net.URL;

import sun.misc.Launcher;

/**
 * Author: Mark
 * Date  : 2017/5/2
 */
public class ClassLoadTest1 {

    public static void main(String[] args) {
//        loadPath();

        ClassLoader classLoader = ClassLoadTest1.class.getClassLoader();
        while (classLoader != null) {
            System.out.println(classLoader);
            classLoader = classLoader.getParent();
        }

    }

    private static void loadPath() {
        final URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println(urL);
        }
        ///
        System.out.println("=====");
        final String property = System.getProperty("sun.boot.class.path");
        final String[] split = property.split(":");
        for (String s : split) {
            System.out.println(s);
        }
    }

}
