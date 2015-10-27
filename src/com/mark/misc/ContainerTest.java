package com.mark.misc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.TreeSet;

/**
 * Author: Mark
 * Date  : 15/10/15
 */
public class ContainerTest {

    public static void main(String[] args) {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add(null);
        hashSet.add("what");
        System.out.println(hashSet.size());
        System.out.println("===========");

        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("what");
//        treeSet.add(null); // throw NullPointerException
        System.out.println(treeSet.size());
        System.out.println("===========");

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(null, null);
        hashMap.put("asdf", "bcd");
        System.out.println(hashMap.size());
        System.out.println("============");

        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("sadf", "fad");
//        hashtable.put(null, null); // throw NullPointerException
        System.out.println(hashtable.size());

    }

}
