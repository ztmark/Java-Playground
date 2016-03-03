package com.mark.concurrency;

/**
 * Author: Mark
 * Date  : 16/3/3.
 */
public class ThreadDemo {


    public static void main(String[] args) {
//        Thread thread = new Thread(() -> {
//            System.out.println("in runnable.");
//        });
//        thread.start();
//        thread.start(); // IllegalStateException
        new PrintTask().start();
    }


    static class PrintTask extends Thread implements Runnable {
        @Override
        public void run() {
            System.out.println("in PrintTask");
        }
    }

}
