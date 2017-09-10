package com.mark.zookeeper;

import java.io.IOException;
import java.nio.file.Watchable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * Author: Mark
 * Date  : 2017/9/10
 */
public class ZookeeperExistUsage implements Watcher {

    private static CountDownLatch connectLatch = new CountDownLatch(1);

    private static ZooKeeper zookeeper;
    private static String path = "/test-zookeeper";

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        zookeeper = new ZooKeeper("localhost:2181", 5000, new ZookeeperExistUsage());
        connectLatch.await();
        final Stat stat = zookeeper.exists(path + "/c1", true);
        zookeeper.create(path + "/c1", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zookeeper.delete(path + "/c1", -1);
        TimeUnit.SECONDS.sleep(1);
    }

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                connectLatch.countDown();
            } else if (Event.EventType.NodeDataChanged == event.getType()) {
                try {
                    System.out.println("Data change " + new String(zookeeper.getData(path, true, null)));
                    zookeeper.exists(path + "/c1", true);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (Event.EventType.NodeDeleted == event.getType()) {
                try {
                    System.out.println("Node delete " + event.getPath());
                    zookeeper.exists(path + "/c1", true);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (Event.EventType.NodeCreated == event.getType()) {
                try {
                    System.out.println("Node created " + event.getPath());
                    zookeeper.exists(path + "/c1", true);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
