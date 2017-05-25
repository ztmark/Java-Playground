package com.mark.async;


import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Author: Mark
 * Date  : 2017/5/25
 */
public class AsyncThreadPoolExecutor implements AsyncExecutor {

    private final Executor threadPool;

    public AsyncThreadPoolExecutor() {
        this.threadPool = Executors.newFixedThreadPool(10);
    }


    @Override
    public <T> AsyncResult<T> start(Callable<T> callable) {
        return start(callable, null);
    }

    @Override
    public <T> AsyncResult<T> start(Callable<T> callable, AsyncCallback<T> callback) {
        final AsyncResultImpl<T> result = new AsyncResultImpl<>(callback);
        threadPool.execute(() -> {
            try {
                result.setValue(callable.call());
            } catch (Exception e) {
                result.setException(e);
            }
        });
        return result;
    }



    private static class AsyncResultImpl<T> implements AsyncResult<T> {

        private static final int RUNNING = 1;
        private static final int SUCCEED = 2;
        private static final int FAILED = 3;

        private int state;
        private T value;
        private Exception e;
        private AsyncCallback<T> callback;
        private final Object lock; // 用来实现 await

        AsyncResultImpl(AsyncCallback<T> callback) {
            this.state = RUNNING;
            this.callback = callback;
            lock = new Object();
        }

        @Override
        public boolean isCompleted() {
            return state > RUNNING;
        }

        @Override
        public T get() throws Exception {
            while (!isCompleted()) {
                synchronized (lock) {
                    lock.wait();
                }
            }
            if (state == FAILED) {
                throw e;
            }
            return value;
        }

        @Override
        public void await() throws InterruptedException {
            while (!isCompleted()) {
                synchronized (lock) {
                    lock.wait();
                }
            }
        }

        void setValue(T value) {
            this.value = value;
            this.state = SUCCEED;
            if (callback != null) {
                callback.onCompleted(this.value, this.e);
            }
            synchronized (lock) {
                lock.notifyAll();
            }
        }

        void setException(Exception e) {
            this.e = e;
            this.state = FAILED;
            if (callback != null) {
                callback.onCompleted(this.value, this.e);
            }
            synchronized (lock) {
                lock.notifyAll();
            }
        }
    }

}
