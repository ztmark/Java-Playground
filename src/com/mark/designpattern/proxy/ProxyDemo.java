package com.mark.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Author: Mark
 * Date  : 2015/4/2
 * Time  : 16:15
 */
public class ProxyDemo {

    public static void main(String[] args) {
        MyConnection conn = new MyDriverManager().getConnection();

        conn.doSomething();
        conn.close();

        InvocationHandler handler = new ConnectionInvocationHandler(conn);

        MyConnection connection = (MyConnection) Proxy.newProxyInstance(conn.getClass().getClassLoader(),
                conn.getClass().getInterfaces(), handler);

        connection.doSomething();
        connection.close();
    }

}
