package com.mark.problem.producerandconsumer;

/**
 * Author: Mark
 * Date  : 15/10/26.
 */
public class Resource {

    private final int CAPACITY;
    private int size = 0;

    public Resource(int capacity) {
        this.CAPACITY = capacity;
    }

    public Resource() {
        this.CAPACITY = 10;
    }

    public synchronized void increase() {
        while (size >= CAPACITY) {
            try {
                System.out.println("produce waiting...");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        size++;
        System.out.println("Produce a Product");
        notifyAll();
    }

    public synchronized void decrease() {
        while (size <= 0) {
            try {
                System.out.println("consume waiting...");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        size--;
        System.out.println("Consume a Product");
        notifyAll();
    }

}
