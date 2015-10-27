package com.mark.designpattern.proxy;

/**
 * Author: Mark
 * Date  : 2015/4/2
 * Time  : 16:47
 */
public class MyConnectionDefault implements MyConnection {
    @Override
    public void close() {
        System.out.println("关闭连接");
    }

    @Override
    public void doSomething() {
        System.out.println("do something");
    }
}
