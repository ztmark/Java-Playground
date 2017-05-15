package com.mark.misc;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Author: Mark
 * Date  : 2017/5/15
 */

class PlusDemo {

    public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Class cache = Integer.class.getDeclaredClasses ()[0];
        System.out.println(cache);
        Field c = cache.getDeclaredField ("cache");
        c.setAccessible (true);
        Integer[] array = (Integer[]) c.get (cache);
        array[132] = array[133];
        System.out.println(Arrays.toString(array));
        System.out.println(2 + 2);
        System.out.printf("%d\n", 2 + 2);
    }

}
