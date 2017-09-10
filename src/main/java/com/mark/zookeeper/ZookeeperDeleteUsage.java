package com.mark.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * Author: Mark
 * Date  : 2017/9/10
 */
public class ZookeeperDeleteUsage implements Watcher {

    private static CountDownLatch connectLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        final ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 5000, new ZookeeperDeleteUsage());
        connectLatch.await();
        zooKeeper.delete("/test-zookeeper/to-be-delete", 0, (rc, path, ctx) -> System.out.println("Success delete znode " + rc + ", " + path + ", " + ctx), "I am context");
        zooKeeper.delete("/test-zookeeper/cant-delete", 0, (rc, path, ctx) -> System.out.println("Success delete znode " + rc + ", " + path + ", " + ctx), "I am context");
        TimeUnit.SECONDS.sleep(1);
    }


    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            connectLatch.countDown();
        }
    }
}
