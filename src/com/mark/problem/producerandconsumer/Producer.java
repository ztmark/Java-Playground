package com.mark.problem.producerandconsumer;

import java.util.Random;

/**
 * Author: Mark
 * Date  : 15/10/26.
 */
public class Producer implements Runnable {

    private Resource resource;

    public Producer(Resource resource) {
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
            resource.increase();
        }
    }
}
