package com.mark.fakemockito;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Mark
 * Date  : 15/10/27.
 */
public class MethodMatcher {


    private Map<MethodParams, MethodDescriptor> matcher = new HashMap<>();


    public Object invoke(Object[] args) {
        MethodParams params = new MethodParams(args);
        MethodDescriptor descriptor = matcher.get(params);
        if (descriptor == null) {
            throw new RuntimeException("this method was not set properly");
        }
        descriptor.increaseInvokeTimes();
        return descriptor.getRetValue();
    }

    public void update(MethodParams params, Object ret) {
        MethodDescriptor descriptor = matcher.get(params);
        if (descriptor == null) {
            matcher.put(params, new MethodDescriptor(ret));
        } else {
            descriptor.setRetValue(ret);
        }
    }

    public void invokeOnce(Object[] args) {
        MethodParams params = new MethodParams(args);
        MethodDescriptor descriptor = matcher.get(params);
        if (descriptor == null) {
            throw new RuntimeException("verify failed");
        }
        if (descriptor.getInvokeTimes() == 1) {
            System.out.println("verify success");
        } else {
            throw new RuntimeException("expected once but invoked " + descriptor.getInvokeTimes() + " times");
        }
    }

}
