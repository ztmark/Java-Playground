package com.mark.java8;

/**
 * Author: Mark
 * Date  : 16/3/23
 */
@FunctionalInterface
public interface FunctionalDefaultMethod {


    void apply();

    default void defaultMethod() {}

    static void staticMethod() {}


}
