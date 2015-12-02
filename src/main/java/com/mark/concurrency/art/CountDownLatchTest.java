package com.mark.concurrency.art;

import java.util.concurrent.CountDownLatch;

/**
 * Author: Mark
 * Date  : 15/12/2.
 */
public class CountDownLatchTest {



    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        new Thread(() -> {
            System.out.println(Thread.currentThread());
            latch.countDown();
        }).start();
        new Thread(() -> {
            System.out.println(Thread.currentThread());
            latch.countDown();
        }).start();
        latch.await();
        System.out.println(Thread.currentThread());
    }

}
