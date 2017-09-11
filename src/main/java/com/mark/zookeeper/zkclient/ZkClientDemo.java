package com.mark.zookeeper.zkclient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * Author: Mark
 * Date  : 2017/9/11
 */
public class ZkClientDemo {

    public static void main(String[] args) throws InterruptedException {
//        connect();
//        create();
//        delete();
//        listener();

        String path = "/zk-book";
        final ZkClient zkClient = new ZkClient("localhost:2181", 5000);
        zkClient.createEphemeral(path, "123");
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("Node " + dataPath + " changed, new data: " + data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("Node " + dataPath + " deleted.");
            }
        });
        System.out.println((String) zkClient.readData(path));
        zkClient.writeData(path, "345");
        TimeUnit.SECONDS.sleep(1);
        zkClient.delete(path);
        TimeUnit.SECONDS.sleep(1);

    }

    private static void listener() throws InterruptedException {
        String path = "/zk-book";
        final ZkClient zkClient = new ZkClient("localhost:2181", 5000);
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println(parentPath + " 's child changed current children " + currentChilds);
            }
        });
        zkClient.createPersistent(path);
        TimeUnit.SECONDS.sleep(1);
        System.out.println(zkClient.getChildren(path));
        TimeUnit.SECONDS.sleep(1);
        zkClient.createPersistent(path + "/c1");
        TimeUnit.SECONDS.sleep(1);
        zkClient.delete(path + "/c1");
        TimeUnit.SECONDS.sleep(1);
        zkClient.delete(path);
        TimeUnit.SECONDS.sleep(1);
    }

    private static void delete() {
        final ZkClient zkClient = new ZkClient("localhost:2181", 5000);
        final boolean delete = zkClient.deleteRecursive("/zk-book");
    }

    private static void create() {
        final ZkClient zkClient = new ZkClient("localhost:2181", 5000);
        zkClient.createPersistent("/zk-book/c2", true);
    }

    private static void connect() {
        final ZkClient zkClient = new ZkClient("localhost:2181", 5000);
        System.out.println("Zookeeper session established");
    }

}
