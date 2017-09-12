package com.mark.zookeeper.zkclient.curator;

import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Author: Mark
 * Date  : 2017/9/12
 */
public class RecipesLock {

    private static String lockPath = "/curator-recipes-lock-path";
    private static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
                                                                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                                                    .build();

    public static void main(String[] args) throws InterruptedException {
        client.start();
        InterProcessLock lock = new InterProcessMutex(client, lockPath);
        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    latch.await();
                    lock.acquire();
                    System.out.println("No: " + System.currentTimeMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        latch.countDown();
    }

}
