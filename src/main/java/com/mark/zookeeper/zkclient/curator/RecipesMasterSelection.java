package com.mark.zookeeper.zkclient.curator;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Author: Mark
 * Date  : 2017/9/12
 */
public class RecipesMasterSelection {

    private static String masterPath = "/curator-recipes-master-path";
    private static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
                                                                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                                                    .build();

    public static void main(String[] args) throws InterruptedException {
        client.start();
        final LeaderSelector selector = new LeaderSelector(client, masterPath, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("become master");
                TimeUnit.SECONDS.sleep(3);
                System.out.println("master operation done");
            }
        });
        selector.autoRequeue();
        selector.start();
        TimeUnit.SECONDS.sleep(5);
    }

}
