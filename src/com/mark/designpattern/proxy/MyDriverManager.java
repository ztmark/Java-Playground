package com.mark.designpattern.proxy;

/**
 * Author: Mark
 * Date  : 2015/4/2
 * Time  : 16:47
 */
public class MyDriverManager {

    public MyConnection getConnection() {
        return new MyConnectionDefault();
    }

}
