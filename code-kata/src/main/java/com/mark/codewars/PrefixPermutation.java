package com.mark.codewars;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Mark
 * @date 2017/10/26
 */
public class PrefixPermutation {

    public static int prefix(int[] a) {
        Set<Integer> sets = new HashSet<>();
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            sets.add(a[i]);
            boolean incr = true;
            for (int j = 0; j < i + 1; j++) {
                if (!sets.contains(j + 1)) {
                    incr = false;
                    break;
                }
            }
            if (incr) {
                count++;
            }
        }
        return count;
    }

    public static int prefixGood(int[] a) {
        final HashSet<Integer> sets = new HashSet<>();
        int max = 0;
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < 1 || sets.contains(a[i])) {
                break;
            }
            max = Math.max(max, a[i]);
            if (max == i + 1) {
                count++;
            }
            sets.add(a[i]);
        }
        return count;
    }

}
