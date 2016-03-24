package com.mark.java8;

/**
 * Author: Mark
 * Date  : 16/3/23
 */
@FunctionalInterface
public interface FunctionalDefaultMethod {


    void apply();

    default void defaultMethod() {
        System.out.println("in default method");
    }

    static void staticMethod() {
        System.out.println("in static method");
    }


    public static void main(String[] args) {
        FunctionalDefaultMethod fdm = () -> {
            System.out.println("in apply");
        };
        fdm.apply();
        fdm.defaultMethod();
        FunctionalDefaultMethod.staticMethod();
//        fdm.staticMethod(); // error

        FunctionalDefaultMethod fdm2 = () -> { staticMethod(); };
        FunctionalDefaultMethod fdm3 = FunctionalDefaultMethod::staticMethod;
        fdm3.apply();
//        FunctionalDefaultMethod fdm4 = () -> { defaultMethod(); }; // non-static method can not referenced from a static context

    }

}
