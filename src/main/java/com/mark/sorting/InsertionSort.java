package com.mark.sorting;

import java.util.Arrays;

/**
 * Author: Mark
 * Date  : 16/2/28.
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] arr = new int[] {4, 6, 23, 65, 2, 54, 65, 34, 2, 55, 12};
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(sort(arr)));
    }


    public static int[] sort(int[] unsorted) {
        int[] res = new int[unsorted.length];
        System.arraycopy(unsorted, 0, res, 0, unsorted.length);
        for (int i = 1; i < res.length; i++) {
            int tmp = res[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (res[j] > tmp) {
                    res[j + 1] = res[j];
                } else {
                    break;
                }
            }
            res[j + 1] = tmp;
        }
        return res;
    }


}
