package com.mark.designpattern.singleton;

import java.util.concurrent.TimeUnit;

/**
 * Author: Mark
 * Date  : 2015/4/17
 * Time  : 9:44
 */
public class SingletonNotThreadSafe {


    public static void main(String[] args) throws InterruptedException {
        final SingletonNotThreadSafe[] instances = new SingletonNotThreadSafe[2];

        new Thread(new Runnable() {
            @Override
            public void run() {
                instances[0] = SingletonNotThreadSafe.getInstance();
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                instances[1] = SingletonNotThreadSafe.getInstance();
            }
        }).start();

        TimeUnit.MILLISECONDS.sleep(2000);  // 等待上面两个线程执行完毕
        System.out.println(instances[0] == instances[1]);  // false
    }

    private static SingletonNotThreadSafe instance;

    private SingletonNotThreadSafe() {}


    public static SingletonNotThreadSafe getInstance() {
        if (instance == null) {
            try {
                Thread.currentThread().sleep(1000); // 休眠，让另一个线程执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (SingletonNotThreadSafe.class) {
                instance = new SingletonNotThreadSafe();
            }
        }
        return instance;
    }

    /*public static SingletonNotThreadSafe getInstance() {
        if (instance == null) {
            try {
                Thread.currentThread().sleep(1000); // 休眠，让另一个线程执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance = new SingletonNotThreadSafe();
        }
        return instance;
    }*/


}
