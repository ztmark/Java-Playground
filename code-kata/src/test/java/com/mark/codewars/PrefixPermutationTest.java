package com.mark.codewars;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Mark
 * @date 2017/10/26
 */
public class PrefixPermutationTest {


    @Test
    public void test() {
        assertEquals(3, PrefixPermutation.prefixGood(new int[]{2, 1, 3, 5, 4}));
        assertEquals(5, PrefixPermutation.prefixGood(new int[]{1, 2, 3, 4, 5}));
    }

}