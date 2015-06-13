package com.mark.problem;

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

    /*
    一个最多能装5个苹果的箱子
    一个人不断向里面放苹果
    一个人不断从里面拿苹果
    模拟这一过程
     */
    public static void main(String[] args) throws InterruptedException {
        Box box = new Box();
        Producer producer = new Producer(box);
        Consumer consumer = new Consumer(box);
        new Thread(producer).start();
        new Thread(consumer).start();
    }


}
