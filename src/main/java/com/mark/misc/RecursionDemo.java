package com.mark.misc;

/**
 * Author: Mark
 * Date  : 16/3/1.
 */
public class RecursionDemo {


    public static void main(String[] args) {
        System.out.println(sum(17678)); // 17689层递归将会出现StackOverflow错误
    }

    private static int sum(int n){
        if (n == 1) {
            return 1;
        }
        return n + sum(n - 1);
    }

}
