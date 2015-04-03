package com.mark.misc;

/**
 * Author: Mark
 * Date  : 2015/3/13
 * Time  : 12:03
 */
public class NullDemo {
    static void greet() {
        System.out.println("Hello World");
    }

    public static void main(String[] args) {
        NullDemo x = null;
        x.greet();
        ((NullDemo)x).greet();
        ((NullDemo)null).greet();
    }
}
