package com.mark.designpattern.singleton;

import java.io.Serializable;

/**
 * Author: Mark
 * Date  : 2015/3/21
 * Time  : 18:51
 */
public class SingletonLazyInit implements Serializable {

    private static SingletonLazyInit instance;

    private SingletonLazyInit () {}

    public static synchronized SingletonLazyInit getInstance() {
        if (instance == null) {
            instance = new SingletonLazyInit();
        }
        return instance;
    }

    private Object readResolve() {
        return instance;
    }

    // other methods

}
