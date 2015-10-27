package com.mark.problem.producerandconsumer;

import java.util.Random;

/**
 * Author: Mark
 * Date  : 15/10/26.
 */
public class Consumer implements Runnable {

    private Resource resource;

    public Consumer(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(new Random().nextInt(3) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resource.decrease();
        }
    }
}
