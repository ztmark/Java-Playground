package com.mark.rxjava;

import java.util.concurrent.CountDownLatch;

import io.reactivex.Observable;

/**
 * Author: Mark
 * Date  : 2017/5/14
 */
public class RxJavaDemoOne {

    public static void main(String[] args) throws InterruptedException {
        int count = 30000;

        final CountDownLatch finishLatch = new CountDownLatch(1);
        long t = System.nanoTime();
        Observable.range(0, count).map(i -> {
            return 200;
        }).subscribe(statusCode -> {

        }, error -> {

        }, finishLatch::countDown);

        finishLatch.await();

        t = (System.nanoTime() - t) / 1000_000;
        System.out.println("RxJava without blocking TPS:" + count * 1000 / t);
    }



}
