package com.mark.collections;

import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeMap;

/**
 * Author: Mark
 * Date  : 2017/4/6
 */
public class MapDemo {

    public static void main(String[] args) {
//        identityMapDemo();
//        hasMap();
//        treeMap();
        final LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>(32, 0.75F, true);
        linkedHashMap.put(null, null);
        linkedHashMap.put("b", null);
        linkedHashMap.put("a", "a");
        System.out.println(linkedHashMap.size());
        System.out.println(linkedHashMap.toString());
        System.out.println(linkedHashMap.get("b"));
        System.out.println(linkedHashMap.toString());
        System.out.println(linkedHashMap.get(null));
        System.out.println(linkedHashMap.toString());
    }

    private static void treeMap() {
        final TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("a", "a");
        treeMap.put("b", null);
        treeMap.put("c", null);
        System.out.println(treeMap.size());
        System.out.println(treeMap.toString());
    }

    private static void hasMap() {
        final HashMap<String, String> map = new HashMap<>();
        map.put(null, null);
        System.out.println(map.size());
        map.put("a", null);
        System.out.println(map.size());
        System.out.println(map.get(null));
        System.out.println(map.get("a"));
        map.put(null, null);
        System.out.println(map.size());
        map.put("b", null);
        System.out.println(map.size());
    }

    private static void identityMapDemo() {
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
