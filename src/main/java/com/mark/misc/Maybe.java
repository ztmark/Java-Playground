package com.mark.misc;

import java.util.function.Function;

/**
 * Author: Mark
 * Date  : 16/4/8
 */
public class Maybe<T> {

    private T value;

    public Maybe(T value) {
        this.value = value;
    }

    private T get() {
        return value;
    }


    public Maybe<T> map(Function<T, T> fn) {
        if (fn == null) {
            return new Maybe<>(null);
        } else {
            return new Maybe<>(fn.apply(value));
        }
    }

    public static void main(String[] args) {
        Maybe<String> maybe = new Maybe<>("home").map(String::toUpperCase);
        System.out.println(maybe.get());
        maybe = new Maybe<>("home").map(null);
        System.out.println(maybe.get());

    }
}
