package com.mark.concurrency;

/**
 * Author: Mark
 * Date  : 2017/7/30
 */
public class WaitNotify {

    volatile int a = 0;

    public static void main(String[] args) {
        demo1();

    }

    private static void demo1() {
        Object object = new Object();
        WaitNotify waitNotify = new WaitNotify();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    System.out.println("线程1 获取到监视器锁");
                    try {
                        object.wait();
                        System.out.println("线程1 正常恢复啦。");
                    } catch (InterruptedException e) {
                        System.out.println("线程1 wait方法抛出了InterruptedException异常");
                    }
                }
            }
        }, "线程1");
        thread1.start();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    System.out.println("线程2 获取到监视器锁");
                    try {
                        object.wait();
                        System.out.println("线程2 正常恢复啦。");
                    } catch (InterruptedException e) {
                        System.out.println("线程2 wait方法抛出了InterruptedException异常");
                    }
                }
            }
        }, "线程2");
//        thread2.setDaemon(true);
        thread2.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    System.out.println("线程3 拿到了监视器锁。");
                    thread1.interrupt();
                    System.out.println("线程3设置了线程1中断");
                    waitNotify.a = 1; // 这行是为了禁止上下的两行中断和notify代码重排序
                    System.out.println("线程3  notify");
                    object.notify();
                    System.out.println("线程3 调用完notify后，休息一会");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                    }
                    System.out.println("线程3 休息够了，结束同步代码块");
                }
            }
        }, "线程3").start();
    }

    private void demo2(){
        Object object = new Object();
        WaitNotify waitNotify = new WaitNotify();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    System.out.println("线程1 获取到监视器锁");
                    try {
                        object.wait();
                        System.out.println("线程1 正常恢复啦。");
                    } catch (InterruptedException e) {
                        System.out.println("线程1 wait方法抛出了InterruptedException异常");
                    }
                }
            }
        }, "线程1");
        thread1.start();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    System.out.println("线程2 获取到监视器锁");
                    try {
                        object.wait();
                        System.out.println("线程2 正常恢复啦。");
                    } catch (InterruptedException e) {
                        System.out.println("线程2 wait方法抛出了InterruptedException异常");
                    }
                }
            }
        }, "线程2");
//        thread2.setDaemon(true); // 线程2会一直在 wait 状态
        thread2.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    System.out.println("线程3 拿到了监视器锁。");
                    object.notify();
                    System.out.println("线程3 notify");
                    waitNotify.a = 1; // 这行是为了禁止上下的两行中断和notify代码重排序
                    System.out.println("线程3设置了线程1中断");
                    thread1.interrupt();
                    System.out.println("线程3 调用完notify后，休息一会");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                    }
                    System.out.println("线程3 休息够了，结束同步代码块");
                }
            }
        }, "线程3").start();

    }

}
