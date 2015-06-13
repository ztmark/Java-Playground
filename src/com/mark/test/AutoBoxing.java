package com.mark.test;

import java.util.List;

/**
 * Author: Mark
 * Date  : 2015/4/17
 * Time  : 22:38
 */
public class AutoBoxing {


    public static void main(String[] args) {
        /*
        Integer a = 1  ===>   Integer.valueOf(1)
        int b = a;  ===>  a.intValue()
        == 不会自动拆箱
        equals 不会处理类型转换
         */
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        System.out.println(g == (a + b));
        System.out.println(g.equals(a + b));
    }


}
