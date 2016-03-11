package com.mark.sorting;

import java.util.Arrays;

/**
 * Author: Mark
 * Date  : 16/3/11.
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = new int[] {6, 4, 23, 65, 2, 54, 65, 34, 2, 55, 12};
        System.out.println(Arrays.toString(arr));
        sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }


    public static void sort(int[] unsorted, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = start + (end - start) / 2;
        sort(unsorted, start, mid);
        sort(unsorted, mid + 1, end);
        merge(unsorted, start, mid, end);
    }

    private static void merge(int[] unsorted, int start, int mid, int end) {
        int[] tmp = new int[end - start + 1];
        System.arraycopy(unsorted, start, tmp, 0, tmp.length);
        int l = 0;
        int tmpMid = mid - start;
        int r = tmpMid + 1;
        int i = start;
        while (l <= tmpMid && r < tmp.length) {
            if (tmp[l] < tmp[r]) {
                unsorted[i++] = tmp[l++];
            } else {
                unsorted[i++] = tmp[r++];
            }
        }
        while (l <= tmpMid) {
            unsorted[i++] = tmp[l++];
        }
        while (r < tmp.length) {
            unsorted[i++] = tmp[r++];
        }
    }


}
