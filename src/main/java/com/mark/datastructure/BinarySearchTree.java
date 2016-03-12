package com.mark.datastructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Mark
 * Date  : 16/3/12.
 */
public class BinarySearchTree {


    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        int[] arr = new int[] {63, 57, 64, 48, 61, 6, 59, 90, 97, 80};
        for (int i = 0; i < arr.length; i++) {
            bst.insert(arr[i]);
        }
        System.out.println(bst.preorder());
        System.out.println(bst.inorder());
        System.out.println(bst.postorder());
        System.out.println("57 successor is " + bst.successor(57));
        System.out.println("57 successor is " + bst.predecessor(57));
        System.out.println("min " + bst.min());
        System.out.println("max " + bst.max());
        bst.remove(57);
        System.out.println(bst.preorder());
        System.out.println(bst.inorder());
        System.out.println(bst.postorder());
        bst.remove(64);
        System.out.println(bst.preorder());
        System.out.println(bst.inorder());
        System.out.println(bst.postorder());
    }


    private Node root;

    public void insert(int value) {

        Node node = new Node(value);
        if (isEmpty()) {
            root = node;
            return;
        }

        if (contains(value)) {
            return;
        }

        Node p = null;
        Node cur = root;
        while (cur != null) {
            p = cur;
            if (cur.value < value) {
                cur = cur.right;
            } else {
                cur = cur.left;
            }
        }
        node.parent = p;
        if (p.value < value) {
            p.right = node;
        } else {
            p.left = node;
        }
    }

    public boolean contains(int value) {
        Node cur = root;
        while (cur != null && cur.value != value) {
            if (cur.value < value) {
                cur = cur.right;
            } else {
                cur = cur.left;
            }
        }
        return cur != null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void remove(int value) {
        Node val = find(value);
        removeNode(val);
    }

    private void removeNode(Node val) {
        if (val == null) {
            throw new RuntimeException("SBT is not contain " + val.value);
        }
        if (val.left == null && val.right == null) {
            if (val.parent == root) {
                root = null;
            } else if (val.parent.left == val){
                val.parent.left = null;
            } else {
                val.parent.right = null;
            }
        } else if (val.left != null) {
            Node max = maxNode(val.left);
            int tmp = val.value;
            val.value = max.value;
            max.value = tmp;
            removeNode(max);
        } else {
            Node min = minNode(val.right);
            int tmp = val.value;
            val.value = min.value;
            min.value = tmp;
            removeNode(min);
        }
    }

    public int min() {
        if (isEmpty()) {
            throw new RuntimeException("BST is empty");
        }
        Node min = minNode(root);
        return min.value;
    }

    private Node minNode(Node root) {
        if (root == null) {
            return null;
        }
        Node cur = root;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    public int max() {
        if (isEmpty()) {
            throw new RuntimeException("BST is empty.");
        }
        return maxNode(root).value;
    }

    private Node maxNode(Node root) {
        if (root == null) {
            return null;
        }
        Node cur = root;
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur;
    }

    private Node find(int value) {
        Node cur = root;
        while (cur != null && cur.value != value) {
            if (cur.value < value) {
                cur = cur.right;
            } else {
                cur = cur.left;
            }
        }
        return cur;
    }

    public int successor(int value) {
        if (!contains(value)) {
            throw new RuntimeException("BST is not contain " + value);
        }
        Node val = find(value);
        if (val.right != null) {
            return minNode(val.right).value;
        }

        Node p = val.parent;
        while (p != null && p.right == val) {
            val = p;
            p = val.parent;
        }
        if (p == null) {
            return Integer.MAX_VALUE; // successor not exist
        } else {
            return p.value;
        }

    }

    public int predecessor(int value) {
        if (!contains(value)) {
            throw new RuntimeException("BST not contains " + value);
        }
        Node val = find(value);
        if (val.left != null) {
            return maxNode(val.left).value;
        }

        Node p = val.parent;
        while (p != null && p.left == val) {
            val = p;
            p = val.parent;
        }
        if (p == null) {
            return Integer.MIN_VALUE; // predecessor not exist
        } else {
            return p.value;
        }
    }


    public List<Integer> preorder() {
        return preOrder(root);
    }

    private List<Integer> preOrder(Node root) {
        List<Integer> order = new ArrayList<>();
        if (root == null) {
            return order;
        }
        order.add(root.value);
        order.addAll(preOrder(root.left));
        order.addAll(preOrder(root.right));
        return order;
    }

    public List<Integer> inorder() {
        return inOrder(root);
    }

    private List<Integer> inOrder(Node root) {
        List<Integer> order = new ArrayList<>();
        if (root == null) {
            return order;
        }
        order.addAll(inOrder(root.left));
        order.add(root.value);
        order.addAll(inOrder(root.right));
        return order;
    }

    public List<Integer> postorder() {
        return postOrder(root);
    }

    private List<Integer> postOrder(Node root) {
        List<Integer> order = new ArrayList<>();
        if (root == null) {
            return order;
        }
        order.addAll(postOrder(root.left));
        order.addAll(postOrder(root.right));
        order.add(root.value);
        return order;
    }



    private static class Node {
        private Node parent;
        private Node left;
        private Node right;
        private int value;

        public Node(int value) {
            this.value = value;
        }
    }
}
