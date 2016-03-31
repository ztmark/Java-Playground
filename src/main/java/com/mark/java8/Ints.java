package com.mark.java8;

/**
 * Author: Mark
 * Date  : 16/3/30
 */
public class Ints {


    public static void main(String[] args) {
        long maxUnsignedInt = (1L << 32) - 1;
        System.out.println("maxUnsignedInt = " + maxUnsignedInt);
        String string = String.valueOf(maxUnsignedInt);
        System.out.println(string);
        int unsignedInt = Integer.parseUnsignedInt(string, 10);
        System.out.println(unsignedInt);
        String string2 = Integer.toUnsignedString(unsignedInt, 10);
        System.out.println(string2);
        try {
            Integer.parseInt(string, 10);
        } catch (NumberFormatException e) {
            System.err.println("could not parse signed int of " + maxUnsignedInt);
        }

        System.out.println("==============");
        try {
            Math.addExact(Integer.MAX_VALUE, 1);
        }
        catch (ArithmeticException e) {
            System.err.println(e.getMessage());
            // => integer overflow
        }
        try {
            Math.toIntExact(Long.MAX_VALUE);
        }
        catch (ArithmeticException e) {
            System.err.println(e.getMessage());
            // => integer overflow
        }
    }

}
