package com.mark;

import java.util.Random;

/**
 * Author: Mark
 * Date  : 2015/4/2
 * Time  : 19:50
 */
public class Productor implements Runnable {

    private Box box;
    private Random random;

    public Productor(Box box) {
        this.box = box;
        random = new Random(47);
    }

    public void produce() {
        while (true) {
            synchronized (box) {
                while (box.isFull()) {
                    try {
                        box.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("produced an apple");
                box.addApple(random.nextInt(100));
                box.notifyAll();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void run() {
        produce();
    }
}
