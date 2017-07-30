package com.mark.concurrency;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Author: Mark
 * Date  : 2017/7/30
 */
public class ThreadSafeMap {

    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> skipListMap = new ConcurrentSkipListMap<>();
        skipListMap.put("A", "10");
        skipListMap.put("D", "8");
        skipListMap.put("Z", "1");
        skipListMap.put("G", "7");
        skipListMap.put("L", "5");
        skipListMap.forEach((key, value) -> System.out.println(key + " -> " + value));
    }

}
