package com.mark.concurrency.art;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Author: Mark
 * Date  : 15/12/2.
 */
public class CyclicBarrierTest implements Runnable {

    private CyclicBarrier barrier = new CyclicBarrier(3, this);

    private ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    private void doWork() {
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), ThreadLocalRandom.current().nextInt(100));
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Override
    public void run() {
        int sum = 0;
        for (Integer i : map.values()) {
            sum += i;
        }
        System.out.println(sum);
    }

    public static void main(String[] args) {
        CyclicBarrierTest cyclicBarrierTest = new CyclicBarrierTest();
        cyclicBarrierTest.doWork();
    }
}
