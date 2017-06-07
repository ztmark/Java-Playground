package com.mark.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Author: Mark
 * Date  : 15/10/29.
 */
public class NoVisibility {
    private static boolean ready;
    private static volatile int number;

    private static class ReaderThread extends Thread {

        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        new ReaderThread().start();
//        number = 32;
//        ready = true;

//        demo1();

        new Thread() {
            @Override
            public void run() {
                while (!ready) {
                    System.out.println(ready);
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        TimeUnit.SECONDS.sleep(1);
        ready = true;
        TimeUnit.SECONDS.sleep(1);
        System.out.println("====");
        System.out.println(ready);

    }

    private static void demo1() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(20);
        for (int i = 0; i < 20; i++) {
            new Thread() {
                @Override
                public void run() {
//                    synchronized (System.out) {
                        for (int j = 0; j < 500; j++) {
                            number++;
                            System.out.println(number);
                        }
//                    }
                    countDownLatch.countDown();
                }
            }.start();
        }
        countDownLatch.await();
        System.out.println("=====");
        System.out.println(number);
    }
}
