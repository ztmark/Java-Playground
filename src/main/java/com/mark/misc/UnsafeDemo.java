package com.mark.misc;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

/**
 * Author: Mark
 * Date  : 2017/6/14
 */
public class UnsafeDemo {

    public static void main(String[] args) {
        final PrivilegedExceptionAction<Unsafe> privilegedAction = () -> {
            final Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        };
        try {
            final Unsafe unsafe = AccessController.doPrivileged(privilegedAction);
            System.out.println(unsafe);
            System.out.println(unsafe.addressSize());

        } catch (PrivilegedActionException e) {
            e.printStackTrace();
        }
    }


}
