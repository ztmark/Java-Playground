package com.mark.javapuzzle;

/**
 * Author: Mark
 * Date  : 2015/6/13
 * Time  : 21:41
 */
public class Creature {

    private static long numCreated = 0;

    public Creature() {
        numCreated++;
    }

    public static synchronized long numCreated() {
        return numCreated;
    }
}
