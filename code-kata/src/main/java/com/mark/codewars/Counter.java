package com.mark.codewars;

import java.util.Arrays;

/**
 * @author Mark
 * @date 2017/10/25
 */
public class Counter {

    public int countSheeps(Boolean[] arrayOfSheeps) {
//        return (int) Arrays.stream(arrayOfSheeps).filter(Objects::nonNull).filter(s -> s).count();
        return (int) Arrays.stream(arrayOfSheeps).filter(Boolean::booleanValue).count();
    }

}
