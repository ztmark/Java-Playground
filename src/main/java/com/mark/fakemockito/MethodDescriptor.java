package com.mark.fakemockito;

/**
 * Author: Mark
 * Date  : 15/10/27.
 */
public class MethodDescriptor {

    private static final Object DEFAULT_RETVALUE = "NO_VALID_VALUE";
    private Object retValue;
    private int invokeTimes = 0;

    public MethodDescriptor(Object retValue) {
        this.retValue = retValue;
    }

    public MethodDescriptor() {
        this(DEFAULT_RETVALUE);
    }

    public void increaseInvokeTimes() {
        invokeTimes++;
    }

    public Object getRetValue() {
        return retValue;
    }

    public void setRetValue(Object retValue) {
        this.retValue = retValue;
    }

    public int getInvokeTimes() {
        return invokeTimes;
    }

}
