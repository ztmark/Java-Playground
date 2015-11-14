package com.mark.misc;

/**
 * Author: Mark
 * Date  : 15/11/14.
 */
public class StaticInitialize {

    static String word = "hello world";

    static {
        System.out.println("in static initialize block.");
    }


}
