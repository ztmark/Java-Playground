package com.mark.learnguava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import java.util.Arrays;

import static com.google.common.base.Preconditions.*;

/**
 * Author: Mark
 * Date  : 15/12/3.
 */
public class Demo1 {

    public static void main(String[] args) {
//        optionalTest();
//        preconditionTest("asb", 1, true);
//        preconditionTest("asb", 11, true);
//        preconditionTest(null, 11, true);
//        preconditionTest("asb", 1, false);
        objectMethodsTest();
    }

    private static void objectMethodsTest() {

        class P implements Comparable<P> {
            String name;
            int age;

            @Override
            public boolean equals(Object o) {
//                Objects.equal(o, this); // infinite recursive
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                P p = (P) o;
                return age == p.age &&
                        Objects.equal(name, p.name);
            }
            @Override
            public int hashCode() {
                return Objects.hashCode(name, age);
            }

            @Override
            public String toString() {
                return MoreObjects.toStringHelper(this)
                        .add("name", name)
                        .add("age", age)
                        .toString();
            }
            @Override
            public int compareTo(P o) {
                return ComparisonChain.start()
                        .compare(this.name, o.name, String.CASE_INSENSITIVE_ORDER)
                        .compare(this.age, o.age)
                        .result();
            }
        }
        P p = new P();
        p.name = "mark";
        p.age = 23;
        System.out.println(p.hashCode());
        System.out.println(ComparisonChain.start().compare("adsf", "Asdf", String.CASE_INSENSITIVE_ORDER).result());
        Ordering<String> ordering = Ordering.natural().onResultOf(String::length);
        System.out.println(ordering.min("asdf", "bcd"));
        System.out.println(ordering.greatestOf(Arrays.asList("asdf", "bc", "zzzzz"), 2)); //
        System.out.println(Objects.equal(null, null));
        System.out.println(Objects.equal("sadf", "asdf"));
        System.out.println(Objects.equal("afa", null));
    }

    private static void preconditionTest(String a, int b, boolean c) {
        String aa = checkNotNull(a, "can not be null", a);
        System.out.println(aa);
        int bb = checkElementIndex(b, 10, "index must legal");  // check if 0 <= b < 10
        System.out.println(bb);
        checkState(c, "must be true");
    }

    private static void optionalTest() {
        Optional<Integer> num = Optional.of(3);
        if (num.isPresent()) {
            System.out.println(num.get());
        }
        num = Optional.absent();
        Integer n = num.orNull(); // null
        System.out.println(n);
        int m = num.or(0);
        System.out.println(m); // 0
    }

}
