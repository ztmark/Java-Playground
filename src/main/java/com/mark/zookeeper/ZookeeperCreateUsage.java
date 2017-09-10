package com.mark.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * Author: Mark
 * Date  : 2017/9/10
 */
public class ZookeeperCreateUsage implements Watcher {

    private static CountDownLatch connectionLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
//        synCreate();
        asyncCreate();
    }

    private static void asyncCreate() throws IOException, InterruptedException {
        final ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 5000, new ZookeeperCreateUsage());
        connectionLatch.await();
        zooKeeper.create("/zk-test-async-ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, (rc, path, ctx, name) -> {
            System.out.println("Create path result: " + rc + ", " + path + ", " + ctx + ", " + name);
        }, "I am context");
        zooKeeper.create("/zk-test-async-ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, (rc, path, ctx, name) -> {
            System.out.println("Create path result: " + rc + ", " + path + ", " + ctx + ", " + name);
        }, "I am context");
        zooKeeper.create("/zk-test-async-ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, (rc, path, ctx, name) -> {
            System.out.println("Create path result: " + rc + ", " + path + ", " + ctx + ", " + name);
        }, "I am context");
        TimeUnit.SECONDS.sleep(1);
    }

    private static void synCreate() throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 5000, new ZookeeperCreateUsage());
        connectionLatch.await();
        final String path = zooKeeper.create("/zk-test-ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Success create znode: " + path);

        final String paths = zooKeeper.create("/zk-test-ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Success create znode: " + paths);
    }


    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            connectionLatch.countDown();
        }
    }
}
