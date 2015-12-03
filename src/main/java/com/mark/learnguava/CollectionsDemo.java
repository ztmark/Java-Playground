package com.mark.learnguava;

import com.google.common.collect.*;

import java.util.Arrays;

/**
 * Author: Mark
 * Date  : 15/12/3.
 */
public class CollectionsDemo {

    public static void main(String[] args) {
//        imSetTest();
    }

    private static void imSetTest() {
        ImmutableSet<String> immutableSet = ImmutableSet.copyOf(Arrays.asList("asdf", "fea"));
        System.out.println(immutableSet);
        immutableSet = ImmutableSet.of();
        System.out.println(immutableSet.isEmpty());
        immutableSet = ImmutableSet.of("ifea", "fane", "sdfd");
        System.out.println(immutableSet);
        immutableSet = ImmutableSet.<String>builder().add("wa").add("what", "the").build();
        System.out.println(immutableSet);
    }


}
