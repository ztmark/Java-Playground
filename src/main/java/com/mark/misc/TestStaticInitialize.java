package com.mark.misc;

import java.lang.reflect.Field;

/**
 * Author: Mark
 * Date  : 15/11/14.
 */
public class TestStaticInitialize {

    public static void main(String[] args) {
        try {
            Class<?> clz = Class.forName("com.mark.misc.StaticInitialize", false, Thread.currentThread().getContextClassLoader());
            for (Field f : clz.getDeclaredFields()) {
                System.out.println(f);
//                System.out.println(f.get(null));
            };
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
