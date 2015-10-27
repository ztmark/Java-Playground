package com.mark.problem.producerandconsumer;

/**
 * Author: Mark
 * Date  : 15/10/26.
 */
public class Platform {

    public static void main(String[] args) {
        Resource resource = new Resource();
        new Thread(new Producer(resource)).start();
        new Thread(new Consumer(resource)).start();
//        new Thread(new Producer(resource)).start();
//        new Thread(new Producer(resource)).start();
    }

}
