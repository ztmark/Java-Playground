package com.mark.designpattern.singleton;

/**
 * Author: Mark
 * Date  : 2015/3/21
 * Time  : 18:53
 */
public class SingletonByHolder {

    public static SingletonByHolder getInstance() {
        return SingletonHolder.instance;
    }

    private SingletonByHolder() {}

    // other methods

    private static class SingletonHolder {
        static final SingletonByHolder instance = new SingletonByHolder();
    }

}
