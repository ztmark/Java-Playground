package com.mark.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

/**
 * Author: Mark
 * Date  : 2017/6/16
 */
public class Master implements Watcher {

    ZooKeeper zooKeeper;
    String hostPort = "127.0.0.1:2181";
    String serverId = Integer.toHexString(31);
    boolean isLeader = false;

    public void startZk() throws IOException {
        zooKeeper = new ZooKeeper(hostPort, 15000, this);
    }

    public void stopZk() throws InterruptedException {
        zooKeeper.close();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    boolean checkMaster() {
        while (true) {
            try {
                Stat stat = new Stat();
                final byte[] data = zooKeeper.getData("/master", false, stat);
                isLeader = new String(data).equals(serverId);
                return true;
            } catch (KeeperException.NoNodeException e) {
                return false;
            } catch (KeeperException.ConnectionLossException e) {
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void runForMaster() throws InterruptedException {
        while (true) {
            try {
                zooKeeper.create("/master", serverId.getBytes(), OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                isLeader = true;
            } catch (KeeperException.NodeExistsException e) {
                isLeader = false;
                break;
            } catch (KeeperException e) {
                e.printStackTrace();
            }
            if (checkMaster()) {
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final Master master = new Master();
        master.startZk();
        master.runForMaster();

        if (master.isLeader) {
            System.out.println("I'm the leader");
        } else {
            System.out.println("Someone else is the leader");
        }

        TimeUnit.SECONDS.sleep(20);
        master.stopZk();
    }
}
