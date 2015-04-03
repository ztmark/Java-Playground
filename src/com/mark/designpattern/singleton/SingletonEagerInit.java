package com.mark.designpattern.singleton;

/**
 * Author: Mark
 * Date  : 2015/3/21
 * Time  : 18:50
 */
public class SingletonEagerInit {

    private static final SingletonEagerInit instance = new SingletonEagerInit();

    private SingletonEagerInit () {}

    public static SingletonEagerInit getInstance() {
        return instance;
    }

    // other methods

}
