package com.mark.designpattern.singleton;

/**
 * Author: Mark
 * Date  : 2015/3/21
 * Time  : 20:17
 */
public class SingletonDoubleChecked {

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

    // other methods

}
