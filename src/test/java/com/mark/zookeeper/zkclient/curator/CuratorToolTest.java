package com.mark.zookeeper.zkclient.curator;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingCluster;
import org.apache.curator.test.TestingServer;
import org.apache.curator.test.TestingZooKeeperServer;
import org.junit.Test;

/**
 * Author: Mark
 * Date  : 2017/9/12
 */
public class CuratorToolTest {


    @Test
    public void testTestingServer() throws Exception {
        final TestingServer testingServer = new TestingServer(2180);
        final CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2180")
                                                               .sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                                               .build();
        client.start();
        System.out.println(client.getChildren().forPath("/zookeeper"));
        testingServer.close();
    }

    @Test
    public void testCluster() throws Exception {
        final TestingCluster cluster = new TestingCluster(3);
        cluster.start();
        TimeUnit.SECONDS.sleep(2);

        TestingZooKeeperServer leader = null;
        for (TestingZooKeeperServer server : cluster.getServers()) {
            System.out.println(server.getInstanceSpec().getServerId() + "-");
            System.out.println(server.getQuorumPeer().getServerState() + "-");

            System.out.println(server.getInstanceSpec().getDataDirectory().getAbsolutePath());
            if (server.getQuorumPeer().getServerState().equals("leading")) {
                leader = server;
            }
        }
        leader.kill();
        System.out.println("after kill leader: ");
        for (TestingZooKeeperServer server : cluster.getServers()) {
            System.out.println(server.getInstanceSpec().getServerId() + "-");
            System.out.println(server.getQuorumPeer().getServerState() + "-");

            System.out.println(server.getInstanceSpec().getDataDirectory().getAbsolutePath());
            if (server.getQuorumPeer().getServerState().equals("leading")) {
                leader = server;
            }
        }
        cluster.stop();
    }
}