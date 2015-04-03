package com.mark;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Author: Mark
 * Date  : 2015/4/2
 * Time  : 19:45
 */
public class Box {

    private final Queue<Integer> box = new LinkedList<>();
    private static final int CAPACITY = 5;
    private int count;

    public boolean isFull() {
        return count == CAPACITY;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void addApple(int a) {
        box.offer(a);
        count++;
    }

    public int removeApple() {
        count--;
        return box.poll();
    }

    public static void main(String[] args) throws InterruptedException {
        Box box = new Box();
        Productor productor = new Productor(box);
        Consumer consumer = new Consumer(box);
        new Thread(productor).start();
        new Thread(consumer).start();
    }


}
