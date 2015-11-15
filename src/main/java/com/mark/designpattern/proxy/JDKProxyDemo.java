package com.mark.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author: Mark
 * Date  : 15/11/15.
 */
public class JDKProxyDemo {

    interface Hello {
        void sayHi();
    }

    static class HelloImpl implements Hello {

        @Override
        public void sayHi() {
            System.out.println("Hello");
        }
    }


    public static void main(String[] args) {
        Hello hi = new HelloImpl();
        Hello hello = (Hello) Proxy.newProxyInstance(hi.getClass().getClassLoader(), hi.getClass().getInterfaces(),
                (proxy, method, args1) -> {
                    System.out.println("before say hi");
                    hi.sayHi();
                    return null;
                });
        hello.sayHi();
    }

}
