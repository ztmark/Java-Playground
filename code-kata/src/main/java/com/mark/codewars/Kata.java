package com.mark.codewars;

import java.util.Arrays;

/**
 * @author Mark
 * @date 2017/10/25
 */
public class Kata {

    public static int myFindEvenIndex(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (Arrays.stream(arr).sum() == 0) {
            return 0;
        }

        // 1,2,3,4,3,2,1
        // 1   3  6 10 13 15 16
        // 16 15 13 10 6  3  1

        final int length = arr.length;
        long[] sumLeft = new long[length];
        long[] sumRight = new long[length];
        for (int i = 0, j = arr.length - 1; i < length; i++, j--) {
            if (i == 0) {
                sumLeft[i] = arr[i];
                sumRight[j] = arr[j];
            } else {
                sumLeft[i] = sumLeft[i - 1] + arr[i];
                sumRight[j] = sumRight[j + 1] + arr[j];
            }
        }
        for (int i = 0; i < length; i++) {
            if (sumLeft[i] == sumRight[i]) {
                return i;
            }
        }
        return -1;
    }


    public static int findEvenIndexGood(int[] arr) {
        int sumRight = Arrays.stream(arr).sum();
        int sumLeft = 0;
        if (sumLeft == sumRight) {
            return 0;
        }
        sumRight -= arr[0];
        for (int i = 1; i < arr.length; i++) {
            sumLeft += arr[i - 1];
            sumRight -= arr[i];
            if (sumLeft == sumRight) return i;
        }
        return -1;
    }

}
