package com.mark.collections;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * Author: Mark
 * Date  : 2017/4/6
 */
public class MapDemo {

    public static void main(String[] args) {
        IdentityHashMap<Value, String> im = new IdentityHashMap<>();
        final Value value = new Value(2, 3);
        final Value value1 = new Value(3, 2);
        final Value value2 = new Value(3, 2);
        im.put(value, "value");
        im.put(value1, "value1");
        im.put(value2, "value2");
        System.out.println(im);
        final Set<Value> es = Collections.newSetFromMap(new IdentityHashMap<>());
        es.add(value);
        es.add(value1);
        es.add(value2);
        System.out.println(es);
    }

    static class Value {
        public int a;
        public int b;

        public Value(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public int hashCode() {
            return a + b;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Value)) {
                return false;
            }
            Value other = (Value) obj;
            return a == other.a && b == other.b;
        }

        @Override
        public String toString() {
            return "[a = " + a + " b = " + b + "]";
        }
    }

}
