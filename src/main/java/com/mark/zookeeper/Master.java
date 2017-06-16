package com.mark.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Author: Mark
 * Date  : 2017/6/16
 */
public class Master implements Watcher {

    ZooKeeper zooKeeper;
    String hostPort = "127.0.0.1:2181";

    public void startZk() throws IOException {
        zooKeeper = new ZooKeeper(hostPort, 15000, this);
    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final Master master = new Master();
        master.startZk();

        TimeUnit.SECONDS.sleep(6);
    }
}
