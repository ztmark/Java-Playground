package com.mark.java8;

import java.io.Console;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Author: Mark
 * Date  : 16/4/6
 */
public class JoinTest {

    public static void main(String[] args) {
//        List<String> ss = IntStream.range(0, 1_150_000).mapToObj(value -> "item"+value).collect(Collectors.toList());
//        String tmp;
//        long start = System.nanoTime();
//        tmp = String.join(",", ss);
//        long duration = System.nanoTime() - start;
//        System.out.println("use join => " + TimeUnit.NANOSECONDS.toMillis(duration));
//        start = System.nanoTime();
//        StringBuilder sb = new StringBuilder();
//        for (String s : ss) {
//            sb.append(s).append(",");
//        }
////        sb.deleteCharAt(sb.length() - 1);
//        tmp = sb.toString();
//        duration = System.nanoTime() - start;
//        System.out.println("use StringBuilder => " + TimeUnit.NANOSECONDS.toMillis(duration));
        Console console = System.console();
        String u = console.readLine("Username: ");
        char[] p = console.readPassword("Password: ");
        System.out.println(u);
        System.out.println(String.valueOf(p));
    }


}
