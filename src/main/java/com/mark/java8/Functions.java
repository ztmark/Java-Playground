package com.mark.java8;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Author: Mark
 * Date  : 16/3/26
 */
public class Functions {

    public static void main(String[] args) {
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);

        System.out.println(backToString.apply("123"));


        Consumer<String> echo = System.out::println;
        echo.accept("mark");

        Optional<String> optional = Optional.of("bam");

        if (optional.isPresent()) {
            System.out.println(optional.get());
        }
        System.out.println(optional.orElse("fallback"));

        optional.ifPresent((s) -> System.out.println(s.charAt(0)));
    }

}
