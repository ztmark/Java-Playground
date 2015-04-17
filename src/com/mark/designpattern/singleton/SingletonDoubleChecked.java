package com.mark.designpattern.singleton;

import java.io.Serializable;

/**
 * Author: Mark
 * Date  : 2015/3/21
 * Time  : 20:17
 */
public class SingletonDoubleChecked implements Serializable {

    private static volatile SingletonDoubleChecked instance;

    private SingletonDoubleChecked () {}

    // effective java : 第71条
    // 使用局部变量可以提供性能
    public static SingletonDoubleChecked getInstance() {
        SingletonDoubleChecked result = instance;
        if (result == null) {
            synchronized(SingletonDoubleChecked.class) {
                result = instance;
                if (result == null) {
                    instance = result = new SingletonDoubleChecked();
                }
            }
        }
        return result;
    }

    private Object readResolve() {
        return instance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    // other methods

}
