package com.mark.concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Author: Mark
 * Date  : 2017/4/10
 */
public class ScheduleDemo {


    public static void main(String[] args) {
//        fixRateDemo();
//        fixDelayDemo();
        fixRateWithMultiThreadDemo();
    }

    private static void fixRateWithMultiThreadDemo() {
        final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("schedule 1 start0 task at " + (System.currentTimeMillis() / 1000));
            try {
                if (ThreadLocalRandom.current().nextInt(5) > 3) {
                    TimeUnit.SECONDS.sleep(10);
                } else {
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("schedule 1 task done at " + (System.currentTimeMillis() / 1000));
//            if (ThreadLocalRandom.current().nextBoolean()) {
//                throw new RuntimeException("oops");
//            }
        }, 1, 2, TimeUnit.SECONDS);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("schedule 2 start0 task at " + (System.currentTimeMillis() / 1000));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("schedule 2 task done at " + (System.currentTimeMillis() / 1000));
        }, 1, 2, TimeUnit.SECONDS);
    }

    private static void fixDelayDemo() {
        final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            System.out.println("start0 task at " + (System.currentTimeMillis() / 1000));
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task done at " + (System.currentTimeMillis() / 1000));
//            if (ThreadLocalRandom.current().nextBoolean()) {
//                throw new RuntimeException("oops");
//            }
        }, 1, 1, TimeUnit.SECONDS);

    }

    private static void fixRateDemo() {
        final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("start0 task at " + (System.currentTimeMillis() / 1000));
            try {
                if (ThreadLocalRandom.current().nextInt(10) > 8) {
                    TimeUnit.SECONDS.sleep(5);
                } else {
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task done at " + (System.currentTimeMillis() / 1000));
            if (ThreadLocalRandom.current().nextBoolean()) {
                throw new RuntimeException("oops");
            }
        }, 1, 2, TimeUnit.SECONDS);
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main wake up");
        scheduledExecutorService.schedule(() -> {
            System.out.println("start0 again");
        }, 1, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("start0 task at " + (System.currentTimeMillis() / 1000));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task done at " + (System.currentTimeMillis() / 1000));
        }, 1, 2, TimeUnit.SECONDS);
    }

}
