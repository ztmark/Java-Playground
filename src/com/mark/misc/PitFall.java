package com.mark.misc;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Mark
 * Date  : 2015/3/13
 * Time  : 10:22
 */
public class PitFall {


    public static void main(String[] args) {
        System.out.println(12345 + 5432l);  // 不要使用 小写的L(l) 来作为后缀
        List l = new ArrayList<String>();
        l.add("Foo");
        System.out.println(1);
        System.out.println(0x100000000L + 0xcafebabe);

        System.out.println(Byte.MAX_VALUE);
        System.out.println(Byte.MIN_VALUE);
        for (byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE; b++) {
            if ((b&0xFF) == 0x99) {
                System.out.println("Found it");
                System.out.println(b);
            }
        }

        byte x = 10;
        System.out.println(x);
        x += 1234;              //复合表达式自动将它们所执行的结果转型为左侧变量的类型
        System.out.println(x);
        x = 10;
        x = (byte) (x + 1234);  // 必须强制转换
        System.out.println(x);
        x = 10;
        System.out.println(x + 1234);

        Object obj = "test";
        String s = "ssss";
        obj = obj + s;
        System.out.println(obj);
//        obj += s;          // 复合表达式只能适用于基本类型、基本类型的包装类型和String类型，String的复合操作左侧必须是String类型


        StringBuilder sb = new StringBuilder();
        sb.append("a").append("b");
        System.out.println(sb.toString());
        StringBuilder sb1 = new StringBuilder('a'); // 这里的'a'转型成int类型，作为capacity参数
        sb1.append('b');
        System.out.println(sb1.toString());
        StringBuilder sb2 = new StringBuilder("a");
        sb2.append("b");
        System.out.println(sb2.toString());

        String s1 = "who";
        System.out.println("who" == s);
        System.out.println("who" == "who");
        System.out.println("who" == new String("who"));
        System.out.println("who" == new String("who").intern());
        System.out.println("who" == s);
        System.out.println(s == s+"");

    }

}
