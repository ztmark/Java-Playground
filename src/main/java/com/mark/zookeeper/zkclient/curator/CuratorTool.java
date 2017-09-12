package com.mark.zookeeper.zkclient.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.ZooKeeper;

/**
 * Author: Mark
 * Date  : 2017/9/12
 */
public class CuratorTool {

    private static String path = "/curator-zkpath-sample";
    private static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").sessionTimeoutMs(5000)
                                                                    .retryPolicy(new ExponentialBackoffRetry(1000, 4))
                                                                    .build();

    public static void main(String[] args) throws Exception {
//        zkPaths();
//        ensurePath();
    }

    private static void ensurePath() throws Exception {
        client.start();
        client.usingNamespace("zk-book");
        final EnsurePath ensurePath = new EnsurePath("/zk-book/c1");
        ensurePath.ensure(client.getZookeeperClient());
        ensurePath.ensure(client.getZookeeperClient());

        final EnsurePath ensurePath1 = client.newNamespaceAwareEnsurePath("/c1");
        ensurePath1.ensure(client.getZookeeperClient());
    }

    private static void zkPaths() throws Exception {
        client.start();
        final ZooKeeper zooKeeper = client.getZookeeperClient().getZooKeeper();
        System.out.println(ZKPaths.fixForNamespace("sub", path));
        System.out.println(ZKPaths.makePath(path, "sub"));

        System.out.println(ZKPaths.getNodeFromPath(path + "/sub2"));

        final ZKPaths.PathAndNode pathAndNode = ZKPaths.getPathAndNode(path + "/sub1");
        System.out.println(pathAndNode.getPath());
        System.out.println(pathAndNode.getNode());

        final String dir1 = path + "/child1";
        final String dir2 = path + "/child2";
        ZKPaths.mkdirs(zooKeeper, dir1);
        ZKPaths.mkdirs(zooKeeper, dir2);
        System.out.println(ZKPaths.getSortedChildren(zooKeeper, path));

        ZKPaths.deleteChildren(client.getZookeeperClient().getZooKeeper(), path, true);
    }


}
