package com.mark.async;

/**
 * Author: Mark
 * Date  : 2017/5/25
 */
public interface AsyncResult<T> {

    boolean isCompleted();

    T get() throws Exception;

    void await() throws InterruptedException;

}
