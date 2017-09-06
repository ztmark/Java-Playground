package com.mark.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * Author: Mark
 * Date  : 2017/9/6
 */
public class ZookeeperDemo1 implements Watcher {

    private static CountDownLatch connectLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException {
        final ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 5000, new ZookeeperDemo1());
        System.out.println(zooKeeper.getState());
        try {
            connectLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Connection established");
        System.out.println(zooKeeper.getState());
        final long sessionId = zooKeeper.getSessionId();
        final byte[] sessionPasswd = zooKeeper.getSessionPasswd();
        System.out.println("sessionId = " + sessionId);


        final ZooKeeper zooKeeper1 = new ZooKeeper("localhost:2181", 5000, new ZookeeperDemo1(), sessionId, "test".getBytes());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(zooKeeper1.getState());
        final ZooKeeper zooKeeper2 = new ZooKeeper("localhost:2181", 5000, new ZookeeperDemo1(), sessionId, sessionPasswd);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(zooKeeper2.getState());

    }


    @Override
    public void process(WatchedEvent event) {
        System.out.println("receive event " + event);
        if (Event.KeeperState.SyncConnected.equals(event.getState())) {
            connectLatch.countDown();
        }
    }
}
