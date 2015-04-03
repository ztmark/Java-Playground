package com.mark.misc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

/**
 * Author: Mark
 * Date  : 2015/3/23
 * Time  : 15:13
 */
public class ArrayListTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<Integer> list3 = new ArrayList<>();
        ArrayList<Date> list4 = new ArrayList<>();
        ArrayList<String> list5 = new ArrayList<>(30);
        Field f1 = list1.getClass().getDeclaredField("elementData");
        Field f2 = list2.getClass().getDeclaredField("elementData");
        Field f3 = list3.getClass().getDeclaredField("elementData");
        Field f4 = list4.getClass().getDeclaredField("elementData");
        Field f5 = list5.getClass().getDeclaredField("elementData");
        f1.setAccessible(true);
        f2.setAccessible(true);
        f3.setAccessible(true);
        f4.setAccessible(true);
        f5.setAccessible(true);
        Object o1 = f1.get(list1);
        Object o2 = f2.get(list2);
        Object o3 = f3.get(list3);
        Object o4 = f4.get(list4);
        Object o5 = f5.get(list5);
        System.out.println(o1);
        System.out.println(o2);
        System.out.println(o3);
        System.out.println(o4);
        System.out.println(o5);
        System.out.println(o1 == o2);
        System.out.println(o1 == o3);
        System.out.println(o1 == o4);
        System.out.println(o1 == o5);
    }
}
