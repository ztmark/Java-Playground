package com.mark.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;

/**
 * Author: Mark
 *
 * Date  : 2017/6/24
 */
public class CuratorDemo {

    public static void main(String[] args) {
        final CuratorFramework zkc = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new RetryOneTime(10));
        try {
            zkc.create().withMode(CreateMode.PERSISTENT).forPath("/mypath", new byte[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final CuratorListener curatorListener = new CuratorListener() {

            @Override
            public void eventReceived(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                try {
                    switch (curatorEvent.getType()) {
                        case CHILDREN:
                            break;
                        case CREATE:
                            break;
                        case DELETE:
                            break;
                        case WATCHED:
                            break;
                    }
                } catch (Exception e) {

                }
            }
        };
        zkc.getCuratorListenable().addListener(curatorListener);
        final LeaderLatch myid = new LeaderLatch(zkc, "/master", "myid");
        myid.addListener(new LeaderLatchListener() {
            @Override
            public void isLeader() {

            }

            @Override
            public void notLeader() {

            }
        });
    }

}
