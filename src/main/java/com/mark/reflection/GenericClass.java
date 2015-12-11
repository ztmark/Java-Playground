package com.mark.reflection;

import java.util.List;

/**
 * Author: Mark
 * Date  : 15/12/11.
 */
public class GenericClass<T> {
    public T val;
    public List<T> list;

    public T method1() {
        return null;
    }

    public List<T> method2() {
        return null;
    }

    public void method3(T v) {

    }

    public void method4(List<String> list) {

    }


    class InnerClass {
        List<String> list;

        public InnerClass() {
        }

        public List<String> method() {
            return null;
        }

        public void method1() {
            System.out.println("InnerClass method1");
        }
    }

}
