package com.mark.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author: Mark
 * Date  : 2017/4/9
 */
public class ThreadPoolDemo {


    public static void main(String[] args) throws InterruptedException {

//        final ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
//        final int i = executorService.prestartAllCoreThreads();
//        executorService.execute(() -> System.out.println("Hello there"));
//        System.out.println(executorService.getActiveCount());
//        System.out.println(executorService.getCorePoolSize());
//        System.out.println(executorService.getLargestPoolSize());
//        TimeUnit.SECONDS.sleep(10);
//        System.out.println(executorService.getActiveCount());
//        System.out.println(executorService.getCorePoolSize());
//        System.out.println(executorService.getLargestPoolSize());
//        executorService.shutdownNow();

        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2));
        threadPoolExecutor.prestartAllCoreThreads();
        threadPoolExecutor.execute(() -> System.out.println("Hello there"));
        for (int i = 0; i < 8; i++) {
            final int i1 = i;
            threadPoolExecutor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(i1);
                    System.out.println("done " + i1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println(threadPoolExecutor.getPoolSize());
        System.out.println(threadPoolExecutor.getActiveCount());
        System.out.println(threadPoolExecutor.getCorePoolSize());
        System.out.println(threadPoolExecutor.getLargestPoolSize());
        System.out.println(threadPoolExecutor.getPoolSize());
        TimeUnit.SECONDS.sleep(10);
        System.out.println(threadPoolExecutor.getActiveCount());
        System.out.println(threadPoolExecutor.getCorePoolSize());
        System.out.println(threadPoolExecutor.getLargestPoolSize());
        System.out.println(threadPoolExecutor.getPoolSize());


        TimeUnit.SECONDS.sleep(5);
        System.out.println(threadPoolExecutor.getActiveCount());
        System.out.println(threadPoolExecutor.getCorePoolSize());
        System.out.println(threadPoolExecutor.getLargestPoolSize());
        System.out.println(threadPoolExecutor.getPoolSize());

        threadPoolExecutor.allowCoreThreadTimeOut(true);

        TimeUnit.SECONDS.sleep(5);
        System.out.println(threadPoolExecutor.getActiveCount());
        System.out.println(threadPoolExecutor.getCorePoolSize());
        System.out.println(threadPoolExecutor.getLargestPoolSize());
        System.out.println(threadPoolExecutor.getPoolSize());

        threadPoolExecutor.shutdownNow();


    }

}
