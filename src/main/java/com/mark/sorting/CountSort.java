package com.mark.sorting;

import java.util.Arrays;

/**
 * Author: Mark
 * Date  : 16/2/27.
 */
public class CountSort {

    public static void main(String[] args) {
        int[] sorted = CountSort.sort(new int[] {16, 4, 10, 14, 7, 9, 3, 2, 8, 1});
        System.out.println(Arrays.toString(sorted));
    }



    public static int[] sort(int[] unsorted) {
        int max = 0, min = 0;
        for (int i : unsorted) {
            if (i > max) {
                max = i;
            }
            if (i < min) {
                min = i;
            }
        }

        int[] count = new int[max - min + 1];
        for (int i : unsorted) {
            count[i]++;
        }
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        int[] sorted = new int[unsorted.length];
        for (int val : unsorted) {
            sorted[count[val] - 1] = val;
            count[val]--;
        }
        return sorted;
    }



}
