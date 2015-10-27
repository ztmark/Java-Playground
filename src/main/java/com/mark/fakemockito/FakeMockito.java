package com.mark.fakemockito;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * Author: Mark
 * Date  : 15/10/27.
 */
public class FakeMockito {

    private static MockContainer mockContainer = new MockContainer();

    public static <T> T mock(Class<T> type) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), type.isInterface()? new Class[]{type} : type.getInterfaces(), new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return mockContainer.invoke(method, args);
            }
        });
    }

    public static MockContainer when(Object obj) {
        return mockContainer;
    }

    public static <T> T verify(T obj) {
        return (T) mockContainer.verify(obj);
    }


    public static void main(String[] args) {
        List list = mock(List.class);
    }

}
