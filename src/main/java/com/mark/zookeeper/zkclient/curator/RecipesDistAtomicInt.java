package com.mark.zookeeper.zkclient.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

/**
 * Author: Mark
 * Date  : 2017/9/12
 */
public class RecipesDistAtomicInt {

    private static String intPath = "/curator-recipes-dist-atomic-int";
    private static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
                                                                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                                                    .build();

    public static void main(String[] args) throws Exception {
        client.start();
        final DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(client, intPath, new RetryNTimes(3, 1000));
        final AtomicValue<Integer> result = atomicInteger.add(3);
        System.out.println("Result: " + result.succeeded() + " " + result.preValue() + " " + result.postValue());
    }

}
