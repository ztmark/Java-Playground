package com.mark.misc;

import java.io.IOException;

/**
 * Author: Mark
 * Date  : 16/3/2.
 */
public class RuntimeDemo {

    public static void main(String[] args) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        System.out.println(runtime.availableProcessors());
        System.out.println(runtime.freeMemory() / 1024 / 1024);
        System.out.println(runtime.maxMemory() / 1024 / 1024);
        System.out.println(runtime.totalMemory() / 1024 / 1024);
    }

}
