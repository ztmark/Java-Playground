package com.mark.problem;

import java.util.Random;

/**
 * Author: Mark
 * Date  : 2015/4/2
 * Time  : 20:25
 */
public class RandNum {

    /*
    随机从0到1000中输出不重复的900个数
     */
    public static void main(String[] args) {
        int[] nums = new int[1000];
        for (int i = 0; i < 1000; i++) {
            nums[i] = i ;
        }
        int cnt = 1000;
        Random random = new Random(47);
        for (int i = 0; i < 900; i++) {
            int index = random.nextInt(cnt--);
            System.out.println(nums[index]);
            int tmp = nums[index];
            nums[index] = nums[cnt];
            nums[cnt] = tmp;
        }
    }

}
