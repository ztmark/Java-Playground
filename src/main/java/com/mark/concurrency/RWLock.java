package com.mark.concurrency;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Author: Mark
 * Date  : 16/3/30
 */
public class RWLock {

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        ExecutorService service = Executors.newFixedThreadPool(2);
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Runnable writerTask = () -> {
            lock.writeLock().lock();
            try {
                map.put("key", "xxx");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
            }
        };
        service.submit(writerTask);

        Runnable readTask = () -> {
            lock.readLock().lock();
            try {
                System.out.println(map.get("key"));
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
            }
        };
        service.submit(readTask);
        service.submit(readTask);

        try {
            service.shutdown();
            service.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            if (!service.isTerminated()) {
                service.shutdownNow();
                System.out.println("shut down");
            }
        }

    }

}
