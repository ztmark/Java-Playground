package com.mark;

import java.util.Queue;

/**
 * Author: Mark
 * Date  : 2015/4/2
 * Time  : 19:48
 */
public class Consumer implements Runnable {

    private Box box;

    public Consumer(Box box) {
        this.box = box;
    }

    public void eat() {
        while (true) {
            synchronized (box) {
                while (box.isEmpty()) {
                    try {
                        box.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int apple = box.removeApple();
                System.out.println("ate an apple");
                box.notifyAll();
            }
        }
    }

    @Override
    public void run() {
        eat();
    }
}
