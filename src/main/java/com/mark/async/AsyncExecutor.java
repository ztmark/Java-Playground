package com.mark.async;

import jdk.nashorn.internal.codegen.CompilerConstants;

import java.util.concurrent.Callable;

/**
 * Author: Mark
 * Date  : 2017/5/25
 */
public interface AsyncExecutor {

    <T> AsyncResult<T> start(Callable<T> callable);

    <T> AsyncResult<T> start(Callable<T> callable, AsyncCallback<T> callback);

}
