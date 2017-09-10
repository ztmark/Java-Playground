package com.mark.zookeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
public class ZookeeperGetUsage implements Watcher {

    private static CountDownLatch connectLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper("localhost:2181", 5000, new ZookeeperGetUsage());
        connectLatch.await();
        String path = "/test-zookeeper";
        final String c1 = zooKeeper.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        final List<String> children = zooKeeper.getChildren(path, true);
        System.out.println(children);
        final String c2 = zooKeeper.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        TimeUnit.SECONDS.sleep(1);
    }

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType()) {
                connectLatch.countDown();
            } else if (Event.EventType.NodeChildrenChanged == event.getType()) {
                System.out.println("child node change");
                try {
                    final List<String> children = zooKeeper.getChildren(event.getPath(), true);
                    System.out.println("Reget children " + children);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
