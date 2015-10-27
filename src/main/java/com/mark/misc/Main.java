package com.mark.misc;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
//        urShift();
        testSwitch();
    }

    public static void testSwitch() {
        int a = 11;
        switch (a) {
            case 1 :
                System.out.println("is one");
                System.out.println("gotcha");
                break;
            case 2 :
                System.out.println("two");
                break;
            case 3 :
                System.out.println("three");
                break;
            default :
                System.out.println("not match");
                break;
            case 5 :
                System.out.println("five");
                break;
            case 12 :
                System.out.println("right");
                break;
            case 13 :
                System.out.println("nono");
        }
    }

    public static void urShift() {
        int i = -1;
        System.out.print(i + " ---> ");
        System.out.println(Integer.toBinaryString(i));
        i >>>= 10;
        System.out.print(i + " ---> ");
        System.out.println(Integer.toBinaryString(i));
        long l = -1;
        System.out.print(l + " ---> ");
        System.out.println(Long.toBinaryString(l));
        l >>>= 10;
        System.out.print(l + " ---> ");
        System.out.println(Long.toBinaryString(l));
        short s = -1;
        System.out.print(s + " ---> ");
        System.out.println(Integer.toBinaryString(s));
        s >>>= 10;
        System.out.print(s + " ---> ");
        System.out.println(Integer.toBinaryString(s));
        byte b = -1;
        System.out.print(b + " ---> ");
        System.out.println(Integer.toBinaryString(b));
        b >>>= 10;
        System.out.print(b + " ---> ");
        System.out.println(Integer.toBinaryString(b));
        b = -1;
        System.out.print(b + " ---> ");
        System.out.println(Integer.toBinaryString(b));
        System.out.print(b + " ---> ");
        System.out.println(Integer.toBinaryString(b >>> 10));
    }

}
