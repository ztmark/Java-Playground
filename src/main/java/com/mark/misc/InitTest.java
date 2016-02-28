package com.mark.misc;

/**
 * Author: Mark
 * Date  : 16/2/28.
 */
public class InitTest {

    public static void main(String[] args) {
//        System.out.println(B.value);
        Base b = new Sub();
    }


}


class A {
    static int value = 100;

    static {
        System.out.println("class A is initialized.");
    }
}

class B extends A {
    static {
        System.out.println("class B is initialized.");
    }
}


class Base {
    private String baseName = "base";

    public Base() {
        callName();
    }

    public void callName() {
        System.out.println(baseName);
    }

    public static void main(String[] args) {
        Base b = new Sub();
    }
}

class Sub extends Base {
    private String baseName = "sub";

    public void callName() {
        System.out.println(baseName);
    }
}
