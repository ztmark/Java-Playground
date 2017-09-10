package com.mark.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * Author: Mark
 * Date  : 2017/9/10
 */
public class ZookeeperSetUsage implements Watcher {

    private static CountDownLatch connectLatch = new CountDownLatch(1);

    private static ZooKeeper zookeeper;
    private static Stat stat = new Stat();
    private static String path = "/test-zookeeper";

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zookeeper = new ZooKeeper("localhost:2181", 5000, new ZookeeperSetUsage());
        connectLatch.await();
        final Stat stat = zookeeper.setData(path, "234".getBytes(), -1); // -1表示基于数据的最新版本
        System.out.println(stat.getVersion());
        System.out.println(stat.getMzxid());
        System.out.println(stat.getCzxid());
        final Stat stat1 = zookeeper.setData(path, "444".getBytes(), stat.getVersion());
        System.out.println(stat1);
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
                    System.out.println(stat.getVersion());
                    System.out.println(stat.getCzxid());
                    System.out.println(stat.getMzxid());
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
