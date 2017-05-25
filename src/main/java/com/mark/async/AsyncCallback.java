package com.mark.async;

/**
 * Author: Mark
 * Date  : 2017/5/25
 */
@FunctionalInterface
public interface AsyncCallback<T> {


    void onCompleted(T value, Exception e);

}
