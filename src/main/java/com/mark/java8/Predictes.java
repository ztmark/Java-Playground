package com.mark.java8;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Author: Mark
 * Date  : 16/3/26
 */
public class Predictes {


    public static void main(String[] args) {
        Predicate<String> predicate = (s) -> s.length() > 0;

        System.out.println(predicate.test("foo"));
        System.out.println(predicate.negate().test("foo"));

        Predicate<Object> nonNull = Objects::nonNull;
        System.out.println(nonNull.test(predicate));
        Predicate<Object> isNull = Objects::isNull;
        System.out.println(isNull.test(null));
        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
        System.out.println(isEmpty.test(""));
        System.out.println(isNotEmpty.test(""));
    }

}
