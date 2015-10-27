package com.mark.misc;

/**
 * Author: Mark
 * Date  : 2015/3/13
 * Time  : 11:46
 */
public class FinallyDemo {

    static int value = 0;
    static int inc() {
        return value++; // 返回的是++之前的值
    }
    static int dec() {
        return value--; // 返回的是--之前的值
    }
    static int getResult() {
        try {
            return inc(); // 先执行inc
        } finally {
            return dec(); // 返回dec的返回值
        }
    }
    //finally里面不允许有return/break/continue/throw等改变正常退出的逻辑
    public static void main(String[] args) {
        System.out.println(getResult());
        System.out.println(value);
    }
}
