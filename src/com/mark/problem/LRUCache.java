package com.mark.problem;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Author: Mark
 * Date  : 15/10/26.
 */
public class LRUCache {

    private HashMap<Integer, Node> cache;
    private int capacity;
    private int count = 0;
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        cache = new HashMap<>(capacity);
        this.capacity = capacity;
        head = new Node(-1, -1, null, null);
        tail = new Node(-1, -1, head, null);
        head.next = tail;
    }

    public int get(int key) {
        Node node = cache.get(key);
        int result = -1;
        if (node != null) { // hit, return and put it to the front
            result = node.value;
            moveToFront(node);
        }
        return result;
    }

    public void set(int key, int value) {
        Node node = cache.get(key);
        if (node != null) {
            node.value = value;
            moveToFront(node);
        } else if (count < capacity) {
            putNode(key, value); // insert a new node
            count++;
        } else {
            // delete the last node
            node = tail.pre;
            node.pre.next = tail;
            tail.pre = node.pre;
            cache.remove(node.key);
            // insert a new node
            putNode(key, value);
            count++;
        }
    }

    private void putNode(int key, int value) {
        Node node;
        node = new Node(key, value, head, head.next);
        head.next.pre = node;
        head.next = node;
        cache.put(key, node);
    }

    private void moveToFront(Node node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
        node.next = head.next;
        node.pre = head;
        head.next.pre = node;
        head.next = node;
    }

    static class Node {

        int key;
        int value;
        Node pre;
        Node next;

        public Node(int key,int value, Node pre, Node next) {
            this.key = key;
            this.value = value;
            this.pre = pre;
            this.next = next;
        }

    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(10);
        cache.set(2, 1);
        int v = cache.get(1);
    }

}
