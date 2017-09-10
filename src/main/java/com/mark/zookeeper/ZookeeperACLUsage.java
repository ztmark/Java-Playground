package com.mark.zookeeper;

import java.io.IOException;
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
public class ZookeeperACLUsage implements Watcher {

    private static CountDownLatch connectLatch = new CountDownLatch(1);

    private static String path1 = "/zk-auth-test";
    private static String path2 = "/zk-auth-test/child";

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zookeeper = new ZooKeeper("localhost:2181", 5000, new ZookeeperACLUsage());
        zookeeper.addAuthInfo("digest", "foo:true".getBytes());
        connectLatch.await();
        zookeeper.create(path1, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        zookeeper.create(path2, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);

        ZooKeeper zooKeeper1 = new ZooKeeper("localhost:2181", 5000, null);
        try {
            zooKeeper1.delete(path2, -1);
        } catch (InterruptedException | KeeperException e) {
            System.out.println("delete node error " + e.getMessage());
        }
        ZooKeeper zooKeeper2 = new ZooKeeper("localhost:2181", 5000, null);
        zooKeeper2.addAuthInfo("digest", "foo:true".getBytes());
        zooKeeper2.delete(path2, -1);

        ZooKeeper zooKeeper3 = new ZooKeeper("localhost:2181", 5000, null);
        zooKeeper3.delete(path1, -1);

        TimeUnit.SECONDS.sleep(1);
    }

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType()) {
                connectLatch.countDown();
            }
        }
    }
}
