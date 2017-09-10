package com.mark.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * Author: Mark
 * Date  : 2017/9/10
 */
public class ZookeeperGetDataUsage implements Watcher {

    private static CountDownLatch connectLatch = new CountDownLatch(1);
    private static ZooKeeper zookeeper;
    private static String path = "/test-zookeeper";
    private static Stat stat = new Stat();

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
//        syncGetData();
        asyncGetData();
    }

    private static void asyncGetData() throws IOException, InterruptedException {
        zookeeper = new ZooKeeper("localhost:2181", 5000, new ZookeeperGetDataUsage());
        connectLatch.await();
        zookeeper.getData(path, true, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                System.out.println(rc);
                System.out.println(path);
                System.out.println(ctx);
                System.out.println(new String(data));
                System.out.println(stat);
            }
        }, "I am context");
        TimeUnit.SECONDS.sleep(1);
    }

    private static void syncGetData() throws IOException, InterruptedException, KeeperException {
        zookeeper = new ZooKeeper("localhost:2181", 5000, new ZookeeperGetDataUsage());
        connectLatch.await();
        final byte[] data = zookeeper.getData(path, true, stat);
        System.out.println("Get data " + new String(data));
        System.out.println(stat);
        final Stat stat = zookeeper.setData(path, "123".getBytes(), 1);
        System.out.println(stat);
        TimeUnit.SECONDS.sleep(1);
    }

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType()) {
                connectLatch.countDown();
            } else if (Event.EventType.NodeDataChanged == event.getType()) {
                try {
                    System.out.println("Data change " + new String(zookeeper.getData(path, true, stat)));
                    System.out.println(stat);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
