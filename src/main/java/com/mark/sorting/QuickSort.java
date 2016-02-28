package com.mark.sorting;

import java.util.Arrays;

/**
 * Author: Mark
 * Date  : 16/2/28.
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = new int[] {4, 6, 23, 65, 2, 54, 65, 34, 2, 55, 12};
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(sort(arr)));
    }


    public static int[] sort(int[] unsorted) {
        int[] res = new int[unsorted.length];
        System.arraycopy(unsorted, 0, res, 0, unsorted.length);
        System.out.println(Arrays.toString(res));
        quickSort(res, 0, res.length - 1);

        return res;
    }

    private static void quickSort(int[] arr, int start, int end) {
        if (end - start <= 0) {
            return;
        }
        movePivotFront(arr, start, end);
        int left = start + 1;
        int right = end;
        while (true) {
            while (arr[left] < arr[start]) {
                left++;
            }
            while (arr[right] > arr[start]) {
                right--;
            }
            if (left >= right) {
                break;
            }
            swap(arr, left, right);
//            left++;
//            right--;
        }
        swap(arr, start, right);
        quickSort(arr, start, right - 1);
        quickSort(arr, right + 1, end);
    }

    private static void movePivotFront(int[] arr, int start, int end) {
        int mid = start + (end - start) / 2;
        if (arr[mid] > arr[end]) {
            swap(arr, mid, end);
        }
        if (arr[start] < arr[mid]) {
            swap(arr, start, mid);
            return;
        }
        if (arr[start] > arr[end]) {
            swap(arr, start, end);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


}
