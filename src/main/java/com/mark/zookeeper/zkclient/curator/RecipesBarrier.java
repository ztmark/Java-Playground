package com.mark.zookeeper.zkclient.curator;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Author: Mark
 * Date  : 2017/9/12
 */
public class RecipesBarrier {

    private static String barrierPath = "/curator-barrier-path";

    public static void main(String[] args) throws Exception {
        barrier();
    }


    private static void barrier() {
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                final CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
                                                                       .sessionTimeoutMs(5000)
                                                                       .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                                                       .build();
                client.start();
                final DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(client, barrierPath, 3);
                try {
                    System.out.println("pre begin...");
                    barrier.enter();
                    System.out.println("begin...");
                    TimeUnit.MILLISECONDS.sleep(Math.round(Math.random() * 1000));
                    barrier.leave();
                    System.out.println("done...");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void latch() throws Exception {
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                final CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
                                                                       .sessionTimeoutMs(5000)
                                                                       .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                                                       .build();
                client.start();
                final DistributedBarrier barrier = new DistributedBarrier(client, barrierPath);
                try {
                    System.out.println("pre begin...");
                    barrier.setBarrier();
                    barrier.waitOnBarrier();
                    System.out.println("begin...");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }


        TimeUnit.SECONDS.sleep(1);
        final CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
                                                               .sessionTimeoutMs(5000)
                                                               .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                                               .build();
        client.start();
        final DistributedBarrier barrier = new DistributedBarrier(client, barrierPath);
        barrier.removeBarrier();
    }

}
