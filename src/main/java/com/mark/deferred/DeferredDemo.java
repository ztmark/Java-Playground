package com.mark.deferred;

import org.jdeferred.AlwaysCallback;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

/**
 * Author: Mark
 * Date  : 2017/4/5
 */
public class DeferredDemo {

    public static void main(String[] args) {
        final DeferredObject<Object, Object, Object> deferredObject = new DeferredObject<>();
        final Promise<Object, Object, Object> promise = deferredObject.promise();
        promise.done(new DoneCallback<Object>() {
            @Override
            public void onDone(Object o) {
                System.out.println("done " + o);
            }
        }).fail(new FailCallback<Object>() {
            @Override
            public void onFail(Object o) {
                System.out.println("fail " + o);
            }
        }).progress(new ProgressCallback<Object>() {
            @Override
            public void onProgress(Object o) {
                System.out.println("on progress " + o);
            }
        }).always(new AlwaysCallback<Object, Object>() {
            @Override
            public void onAlways(Promise.State state, Object o, Object o2) {
                System.out.println("always " + state + " o " + o + " o2 " + o2 );
            }
        });
        deferredObject.notify("start");
        deferredObject.reject("oops");
//        deferredObject.resolve("hello");

    }

}
