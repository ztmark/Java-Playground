package com.mark.timer;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Author: Mark
 * Date  : 2017/3/22
 */
public class CustomScheduleTask {


    static class Timer {
        private static final int length = 3600;
        @SuppressWarnings("unchecked")
        private Set<Task>[] tasks = new Set[length];
        private int currentIndex = 0;
        private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        private ExecutorService executorService = Executors.newCachedThreadPool();


        Timer() {
            for (int i = 0; i < length; i++) {
                tasks[i] = new HashSet<>();
            }
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                final Set<Task> taskSet = tasks[currentIndex];
                for (Task task : taskSet) {
                    if (task.life == 0) {
                        executorService.execute(task.runnable);
                    } else {
                        task.life--;
                    }
                }
                currentIndex = (currentIndex + 1) % length;
            }, 10, 1, TimeUnit.SECONDS);
        }

        public void addTask(Runnable task, int delayTimeInSeconds) {
            int life = (currentIndex + delayTimeInSeconds) / length;
            int index = (currentIndex + delayTimeInSeconds) % length;
            final Task tmp = new Task(life, task);
            tasks[index].add(tmp);
        }

        public void shutDown() {
            scheduledExecutorService.shutdown();
            try {
                executorService.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        class Task {
            private int life;
            private Runnable runnable;

            Task(int life, Runnable runnable) {
                this.life = life;
                this.runnable = runnable;
            }
        }
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.addTask(() -> System.out.println("how are you"), 10);
        timer.addTask(() -> System.out.println("fine thank you"), 20);
        timer.addTask(() -> System.out.println("and you"), 30);
        timer.addTask(() -> System.out.println("I'am fine too"), 40);
        try {
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.shutDown();
    }

}
