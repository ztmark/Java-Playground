package com.mark.java8;

import java.lang.annotation.*;
import java.util.Arrays;

/**
 * Author: Mark
 * Date  : 16/3/26
 */
@Hint("v1")
@Hint("v2")
public class AnnotationSample {

    public static void main(String[] args) {
        Annotation annotation = AnnotationSample.class.getAnnotation(Hint.class);
        System.out.println(annotation);
        Annotation hints = AnnotationSample.class.getAnnotation(Hints.class);
        System.out.println(hints);
        Annotation[] hint = AnnotationSample.class.getAnnotationsByType(Hint.class);
        System.out.println(Arrays.toString(hint));
    }


}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Hints {
    Hint[] value();
}

@Repeatable(Hints.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Hint {
    String value();
}