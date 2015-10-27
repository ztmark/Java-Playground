package com.mark.fakemockito;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Mark
 * Date  : 15/10/27.
 */
public class MockContainer {


    private Map<Method, MethodMatcher> methods = new HashMap<>();
    private Method preMethod;
    private MethodParams preParams;
    private boolean verify = false;

    public Object invoke(Method method, Object[] args) {
        MethodMatcher matcher = methods.get(method);
        if (verify) {
            if (matcher == null) {
                throw new RuntimeException("verify failed");
            }
            verify = false;
            matcher.invokeOnce(args);
            return null;
        }
        if (matcher == null) {
            preMethod = method;
            preParams = new MethodParams(args);
            methods.put(method, new MethodMatcher());
            return null;
        } else {
            return matcher.invoke(args);
        }
    }

    public void thenReturn(Object ret) {
        methods.get(preMethod).update(preParams, ret);
    }

    public Object verify(Object obj) {
        verify = true;
        return obj;
    }
}
