package com.mark.probability;

import java.util.Random;

/**
 * Author: Mark
 * Date  : 15/11/11.
 */
public class MontyHall {

    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis());
        // not change
        long start = System.nanoTime();
        int totalTimes = 10_000_000;
        int winTimes = 0;
        for (int i = 0; i < totalTimes; i++) {
            int choose = random.nextInt(3) + 1;
            int winDoor = random.nextInt(3) + 1;
            if (choose == winDoor) winTimes++;
        }
        System.out.printf("win probability: %f.\n", winTimes * 1.0 / totalTimes);
        long duration = System.nanoTime() - start;
        System.out.println("it takes " + (duration * 1.0 / 1_000_000_000) + " seconds");
        // change is the opposite
    }

    private static void monty() {

    }

}
