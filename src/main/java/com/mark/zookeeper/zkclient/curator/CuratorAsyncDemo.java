package com.mark.zookeeper.zkclient.curator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * Author: Mark
 * Date  : 2017/9/12
 */
public class CuratorAsyncDemo {

    private static String path = "/zk-book";
    private static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").sessionTimeoutMs(5000)
                                                                    .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
    private static CountDownLatch semaphore = new CountDownLatch(2);
    private static ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws Exception {
        client.start();
        System.out.println("Main thread: " + Thread.currentThread().getName());
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println(event);
                System.out.println("process thread " + Thread.currentThread().getName());
                semaphore.countDown();
            }
        }, executorService).forPath(path, "init".getBytes());

        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println(event);
                System.out.println("process thread " + Thread.currentThread().getName());
                semaphore.countDown();
            }
        }).forPath(path, "init".getBytes());

        semaphore.await();
        executorService.shutdown();
    }

}
