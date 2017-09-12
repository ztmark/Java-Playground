package com.mark.zookeeper.zkclient.curator;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * Author: Mark
 * Date  : 2017/9/12
 */
public class NodeListenerDemo {

    private static String path = "/zk-book";
    private static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").sessionTimeoutMs(5000)
                                                                    .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();

    public static void main(String[] args) throws Exception {
//        nodeChange();
        childrenNodeChange();
    }

    private static void childrenNodeChange() throws Exception {
        client.start();
        final PathChildrenCache cache = new PathChildrenCache(client, path, true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener((curatorFramework, pathChildrenCacheEvent) -> {
            switch (pathChildrenCacheEvent.getType()) {
                case CHILD_ADDED:
                    System.out.println("add child " + pathChildrenCacheEvent.getData());
                    break;
                case CHILD_UPDATED:
                    System.out.println("update child " + pathChildrenCacheEvent.getData());
                    break;
                case CHILD_REMOVED:
                    System.out.println("remove child " + pathChildrenCacheEvent.getData());
                    break;
                default:
                    break;
            }
        });

        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path + "/c1");
        TimeUnit.SECONDS.sleep(1);
        client.setData().forPath(path + "/c1", "new".getBytes());
        TimeUnit.SECONDS.sleep(1);
        client.delete().deletingChildrenIfNeeded().forPath(path + "/c1");
        TimeUnit.SECONDS.sleep(1);
        client.delete().deletingChildrenIfNeeded().forPath(path);
        TimeUnit.SECONDS.sleep(1);
    }

    private static void nodeChange() throws Exception {
        client.start();
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, "init".getBytes());
        final NodeCache nodeCache = new NodeCache(client, path, false);
        nodeCache.start(true);
        nodeCache.getListenable().addListener(() -> System.out.println("Node change " + new String(nodeCache.getCurrentData().getData())));
        client.setData().forPath(path, "new".getBytes());
        TimeUnit.SECONDS.sleep(1);
        client.delete().deletingChildrenIfNeeded().forPath(path);
        TimeUnit.SECONDS.sleep(1);
    }

}
