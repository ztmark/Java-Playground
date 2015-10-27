package com.mark.misc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author: Mark
 * Date  : 15/10/27.
 */
public class FakeMockito {

    static interface TestOrigin {
        public void boom();

        public void safe();
    }

    static class Test {

        public void boom() {
            System.out.println("boom");
        }
        public void safe() {
            System.out.println("safe");
        }

    }


    public static <T> T mock(Class<T> type) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("boom")) {
                    throw new RuntimeException("Boom...");
                }
                System.out.println("invoke " + method.getName());
                return method.invoke(proxy, args);
            }
        });
    }


    public static void main(String[] args) {
        TestOrigin test = (TestOrigin) FakeMockito.mock(TestOrigin.class);
        test.safe();
        test.boom();
    }

}
