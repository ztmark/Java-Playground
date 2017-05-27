package com.mark.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Mark
 * Date  : 2017/5/27
 */
@CustomAnnotationDemo.Identify(value = "demo")
public class CustomAnnotationDemo {

    @Inherited
    @Documented
    @Target({ElementType.TYPE, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Identify {

        String a = ""; // 默认是 public static final

        int b = 0;

        String value() default "";
    }

    public static void main(String[] args) {
        if (CustomAnnotationDemo.class.isAnnotationPresent(Identify.class)) {
            final Identify annotation = CustomAnnotationDemo.class.getAnnotation(Identify.class);
            final String value = annotation.value();
            System.out.println("CustomAnnotationDemo's identify is " + value);
            System.out.println(Identify.a);
            System.out.println(Identify.b);
        }
    }

}
