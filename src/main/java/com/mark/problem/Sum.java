package com.mark.problem;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Author: Mark
 * Date  : 15/11/25.
 *
 *
 * 有一个很大的整数链表，需要求这个链表中所有整数的和，写一个可以充分利用多核CPU的代码来计算结果。
 *
 */
public class Sum {


    public static void main(String[] args) throws InterruptedException {
        SingleLinkedList<Integer> nums = makeNums(100_000_000);
        long start = System.currentTimeMillis();
        int sum = sumInSerial(nums);
        long duration = System.currentTimeMillis() - start;
        System.out.println("sum in serial: " + sum + " and it takes " + duration + " milliseconds");
        start = System.currentTimeMillis();
        sum = sumInParallel(nums);
        duration = System.currentTimeMillis() - start;
        System.out.println("sum in parallel: " + sum + " and it takes " + duration + " milliseconds");
    }

    public static SingleLinkedList<Integer> makeNums(int n) {
        SingleLinkedList<Integer> list = new SingleLinkedList<>();
        Random random = new Random(System.currentTimeMillis() / 47);
        for (int i = 0; i < n; i++) {
            list.add(random.nextInt(1000));
        }
        return list;
    }


    public static int sumInSerial(SingleLinkedList<Integer> nums) {
        int res = 0;
        for (Integer num : nums) {
            res += num;
        }
        return res;
    }

    public static int sumInParallel(SingleLinkedList<Integer> nums) throws InterruptedException {
        int threads = Runtime.getRuntime().availableProcessors();
        int[] eachSum = new int[threads];
        CountDownLatch latch = new CountDownLatch(threads);
        for (int i = 0; i < threads; i++) {
            new Thread(new SumTask(i, latch, eachSum, nums)).start();
        }
        latch.await();
        int sum = 0;
        for (int i : eachSum) {
            sum += i;
        }
        return sum;
    }

    static class SumTask implements Runnable {

        private int n;
        private CountDownLatch latch;
        private int[] sums;
        private SingleLinkedList<Integer> nums;

        public SumTask(int n, CountDownLatch latch, int[] sums, SingleLinkedList<Integer> nums) {
            this.n = n;
            this.latch = latch;
            this.sums = sums;
            this.nums = nums;
        }

        @Override
        public void run() {
            int sum = 0;
            Node<Integer> cur = nums.header;
            int gap = n;
            while (gap-- > 0 && cur.next != null) {
                cur = cur.next;
            }
            if (cur.next == null) return;
            out:while (true) {
                sum += cur.next.value;
                for (int i = 0; i < sums.length; i++) {
                    cur = cur.next;
                    if (cur.next == null) {
                        break out;
                    }
                }
            }
            sums[n] = sum;
            latch.countDown();
        }
    }

    static class SingleLinkedList<T> implements Iterable<T> {
        private Node<T> header = new Node<>(null);

        public void add(T val) {
            Node<T> newNode = new Node<>(val);
            newNode.next = header.next;
            header.next = newNode;
        }


        @Override
        public Iterator<T> iterator() {
            return new Iter(header);
        }

        class Iter implements Iterator<T> {

            private Node<T> cur;

            public Iter(Node<T> header) {
                this.cur = header;
            }

            @Override
            public boolean hasNext() {
                return cur.next != null;
            }

            @Override
            public T next() {
                T res = cur.next.value;
                cur = cur.next;
                return res;
            }
        }
    }

    static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }

}
