package com.mark.designpattern.proxy;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * Author: Mark
 * Date  : 15/11/15.
 */
public class CglibDemo {

    static class Hello {

        public void sayHi() {
            System.out.println("hi");
        }

        public void sayGoodBye() {
            System.out.println("Goodbye");
        }

    }


    static class MyInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("before method invoked");
            return proxy.invokeSuper(obj, args);
        }
    }

    static class AnotherInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("it's time...");
            return proxy.invokeSuper(obj, args);
        }
    }


    public static void main(String[] args) {
        // normal use
        Hello hello = new Hello();
        hello.sayHi();
        hello.sayGoodBye();
        System.out.println("=====================");

        // dynamic proxy by cglib
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Hello.class);
//        enhancer.setCallback(new MyInterceptor());
//        enhancer.setCallbacks(new Callback[] {new MyInterceptor(), NoOp.INSTANCE});
        enhancer.setCallbacks(new Callback[] {new MyInterceptor(), new AnotherInterceptor()});
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                if ("sayHi".equals(method.getName())) {
                    return 0;  // 表示使用第一个拦截器 即 callbacks[0]
                }
                return 1; // 表示使用第二个拦截器 即 callbacks[1]
            }
        });
        Hello hello1 = (Hello) enhancer.create();
        hello1.sayHi();
        hello1.sayGoodBye();
    }

}
