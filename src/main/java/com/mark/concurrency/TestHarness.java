package com.mark.concurrency;

import java.util.concurrent.CountDownLatch;

/**
 * Author: Mark
 * Date  : 15/10/30.
 */
public class TestHarness {


    public static long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignore) {
                    }
                }
            };
            t.start();
        }
        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return end - start;
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep((long)(Math.random() * 5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(timeTasks(Runtime.getRuntime().availableProcessors(), task));
    }

}
