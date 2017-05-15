package com.mark.misc;

/**
 * Author: Mark
 * Date  : 2017/5/15
 */
class Unsound {

    static class Constrain<A, B extends A> {}

    static class Bind<A> {
        <B extends A> A upcast(Constrain<A, B> constrain, B b) {
            return b;
        }

    }

    static <T, U> U coerce(T t) {
        Constrain<U, ? super T> constrain = null;
        Bind<U> bind = new Bind<U>();
//        return bind.upcast(constrain, t);
        return null;
    }

    public static void main(String[] args) {
        String zero = Unsound.<Integer, String>coerce(0);
    }

}

