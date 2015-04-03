package com.mark.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Author: Mark
 * Date  : 2015/4/2
 * Time  : 16:03
 */
public class ConnectionInvocationHandler implements InvocationHandler {

    private MyConnection connection;

    public ConnectionInvocationHandler(MyConnection connection) {
        this.connection = connection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("close")) {
            System.out.println("放入连接池。");
            return null;
        }
        return method.invoke(connection, args);
    }

}
