package com.mark.concurrency.art;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * Author: Mark
 * Date  : 15/12/2.
 */
public class ExchangerTest {


    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(() -> {
            try {
                System.out.println(exchanger.exchange("bbbbb"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.SECONDS.sleep(1);
        String v1 = exchanger.exchange("aaa");
        System.out.println(v1);
        new Thread(() -> {
            try {
                System.out.println(exchanger.exchange("ccccc"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.SECONDS.sleep(1);
        String v2 = exchanger.exchange("bbb");
        System.out.println(v2);
    }

}
