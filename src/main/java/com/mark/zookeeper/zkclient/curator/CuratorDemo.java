package com.mark.zookeeper.zkclient.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import scala.math.Ordering;

/**
 * Author: Mark
 * Date  : 2017/9/11
 */
public class CuratorDemo {

    private static CuratorFramework client;
    private static String path = "/zk-book/c1";

    public static void main(String[] args) throws Exception {
        create();
//        createNode();
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, "init".getBytes());
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        System.out.println(stat);
        stat = client.setData().withVersion(stat.getVersion()).forPath(path, "234".getBytes());
        final byte[] bytes = client.getData().forPath(path);
        System.out.println("----");
        System.out.println(new String(bytes));
        System.out.println("----");
        client.delete().deletingChildrenIfNeeded().withVersion(stat.getVersion()).forPath(path);
    }

    private static void createNode() throws Exception {
        String c1 = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, "init".getBytes());
    }

    private static void create() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
//        final CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", 5000, 3000, retryPolicy);
        client = CuratorFrameworkFactory.builder().connectString("localhost:2181").sessionTimeoutMs(5000).retryPolicy(retryPolicy).namespace("base").build();
        client.start();
    }

}
