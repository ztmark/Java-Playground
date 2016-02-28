package com.mark.sorting;

import java.util.Arrays;

/**
 * Author: Mark
 * Date  : 16/2/28.
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] arr = new int[] {4, 6, 23, 65, 2, 54, 65, 34, 2, 55, 12};
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(sort(arr)));
    }

    public static int[] sort(int[] unsorted) {
        int[] res = new int[unsorted.length];
        System.arraycopy(unsorted, 0, res, 0, unsorted.length);

        buildHeap(res);
        for (int i = res.length - 1; i > 0; i--) {
            swap(res, 0, i);
            percDown(res, 0, i);
        }

        return res;
    }

    private static void swap(int[] res, int i, int j) {
        int tmp = res[i];
        res[i] = res[j];
        res[j] = tmp;
    }

    private static void buildHeap(int[] arr) {
        for (int i = arr.length / 2; i >= 0; i--) {
            percDown(arr, i, arr.length);
        }

    }

    private static void percDown(int[] arr, int i, int end) {
        int tmp = arr[i];
        while (leftChild(i) < end) {
            int child = leftChild(i);
            if (child + 1 < end && arr[child] < arr[child + 1]) {
                child++;
            }
            if (arr[child] < tmp) {
                break;
            }
            arr[i] = arr[child];
            i = child;
        }
        arr[i] = tmp;
    }

    private static int leftChild(int p) {
        return p * 2 + 1;
    }

}
