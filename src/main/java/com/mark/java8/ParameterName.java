package com.mark.java8;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Author: Mark
 * Date  : 16/3/23
 */
public class ParameterName {

    public static void main(String[] args) throws NoSuchMethodException {
        Method method = ParameterName.class.getMethod("main", String[].class);
        for (Parameter parameter : method.getParameters()) {
            System.out.println(parameter.getName());
        }
    }


}
