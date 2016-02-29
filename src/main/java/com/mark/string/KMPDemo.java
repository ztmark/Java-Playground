package com.mark.string;

import java.util.Arrays;
import java.util.Random;

/**
 * Author: Mark
 * Date  : 16/2/29.
 */
public class KMPDemo {

    private static char[] chars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g'};

    public static void main(String[] args) {
//        System.out.println(Arrays.toString(next("ababacb")));
//        System.out.println(Arrays.toString(next("abcdabd")));
//        System.out.println(Arrays.toString(next("abcabd")));

//        testNextOptionCount();

//        String text = "abcabcabdabba";
//        String pattern = "abcabd";
//        System.out.println(Arrays.toString(next(pattern)));
//        int shift = kmp(text, pattern);
//        System.out.println(pattern);
//        System.out.println(text.substring(shift, shift + pattern.length()));

    }



    private static int kmp(String text, String pattern) {
        int[] next = next(pattern);
        int j = 0;
        int cnt = 0;
        for (int i = 0; i < text.length(); i++) {
            while (true) {
                cnt++;
                if (j == pattern.length()) {
                    System.out.println("text["+text.length()+"] pattern["+pattern.length()+"] costs " + cnt);
                    return i - j;
                }
                if (text.charAt(i) == pattern.charAt(j)) {
                    j++;
                    break;
                } else if (j == 0){
                    break;
                } else {
                    j = next[j - 1];
                }
            }
        }
        System.out.println("text["+text.length()+"] pattern["+pattern.length()+"] costs " + cnt);
        return -1;
    }

    private static int[] next(String pattern) {
        int[] next = new int[pattern.length()];
        next[0] = 0;
//        int cnt = 0;
        for (int i = 1; i < pattern.length(); i++) {
            int j = i - 1;
            while (j >= 0) {
//                cnt++;
                if (pattern.charAt(next[j]) == pattern.charAt(i)) {
                    next[i] = next[j] + 1;
                    break;
                } else if (next[j] == 0){
                    next[i] = 0;
                    break;
                } else {
                    j = next[j - 1];
                }
            }
        }
//        System.out.println("next length of "+next.length+" costs " + cnt);
        return next;
    }

    private static void testNextOptionCount() {
        for (int i = 10; i < 100000; i *= 2) {
            next(randomString(i));
        }
    }

    private static String randomString(int n) {
        StringBuilder sb = new StringBuilder(n);
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < n; i++) {
            sb.append(chars[random.nextInt(chars.length)]);
        }
        return sb.toString();
    }

}
