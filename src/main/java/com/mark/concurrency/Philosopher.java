package com.mark.concurrency;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author: Mark
 * Date  : 16/3/11.
 */
public class Philosopher implements Runnable {

    private volatile boolean eating;
    private ReentrantLock table;
    private Philosopher left, right;
    private Condition condition;


    public static void main(String[] args) {
        ReentrantLock table = new ReentrantLock();
        Philosopher thread1 = new Philosopher(table);
        Philosopher thread2 = new Philosopher(table);
        Philosopher thread3 = new Philosopher(table);
        Philosopher thread4 = new Philosopher(table);
        Philosopher thread5 = new Philosopher(table);

        thread1.setLeft(thread5);
        thread1.setRight(thread2);
        thread2.setLeft(thread1);
        thread2.setRight(thread3);
        thread3.setLeft(thread2);
        thread3.setRight(thread4);
        thread4.setLeft(thread3);
        thread4.setRight(thread5);
        thread5.setLeft(thread4);
        thread5.setRight(thread1);

        Thread threada = new Thread(thread1);
        threada.setName("phi-1");
        threada.start();
        Thread threadb = new Thread(thread2);
        threadb.setName("phi-2");
        threadb.start();
        Thread threadc = new Thread(thread3);
        threadc.setName("phi-3");
        threadc.start();
        Thread threadd = new Thread(thread4);
        threadd.setName("phi-4");
        threadd.start();
        Thread threade = new Thread(thread5);
        threade.setName("phi-5");
        threade.start();

    }



    public Philosopher(ReentrantLock table) {
        this.table = table;
        condition = table.newCondition();
    }

    public void setLeft(Philosopher left) {
        this.left = left;
    }

    public void setRight(Philosopher right) {
        this.right = right;
    }

    @Override
    public void run() {
        try {
            while (true) {
                thinking();
                eating();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void thinking() throws InterruptedException {
        try {
            table.lock();
            eating = false;
            left.condition.signal();
            right.condition.signal();
        } finally {
            table.unlock();
        }
        System.out.println(Thread.currentThread().getName() + " is thinking.");
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
    }

    private void eating() throws InterruptedException {
        try {
            table.lock();
            while (left.eating || right.eating) {
                condition.await();
            }
            eating = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            table.unlock();
        }
        System.out.println(Thread.currentThread().getName() + " is eating.");
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
    }
}
