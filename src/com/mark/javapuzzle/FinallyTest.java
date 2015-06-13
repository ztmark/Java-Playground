package com.mark.javapuzzle;

/**
 * Author: Mark
 * Date  : 2015/6/13
 * Time  : 21:38
 */
public class FinallyTest {

    public static void main(String[] args) {
        System.out.println(decision());
    }

    static boolean decision() {
        try {
            return true;
        } finally {
            return false;
        }
    }

}
