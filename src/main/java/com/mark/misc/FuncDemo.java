package com.mark.misc;

import java.util.function.Function;

/**
 * Author: Mark
 * Date  : 15/11/21.
 */
public class FuncDemo {

    public static <T> Function<T, T> id() {
        return t -> t;
    }

    public static <T, A, B> Function<A, T> comfun(Function<A, B> f, Function<B, T> g) {
        return (A a) -> g.apply(f.apply(a));
    }

    public static void main(String[] args) {
        Function<String,Integer> f = comfun(id(), String::length);
        System.out.println(f.apply("hello"));
    }

}
