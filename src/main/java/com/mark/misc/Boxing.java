package com.mark.misc;

/**
 * Author: Mark
 * Date  : 2018/1/19
 */
public class Boxing {

    public static void main(String[] args) {
        long start = System.nanoTime();
        long sum = 0L;
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        System.out.println(sum);
        System.out.println(System.nanoTime() - start);
    }

}
