package com.mark.problem;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Author: Mark
 * Date  : 2017/4/29
 */
public class TreeifyTest {


    @Test
    public void test_empty() {
        Set<Node> set = new HashSet<>();
        final String jsonStr = Treeify.treeifySet(set);
        final String jsonString = Treeify.treeifySetOptimized(set);
        assertThat("", is(jsonStr));
        assertThat("", is(jsonString));
    }

    @Test
    public void test_one_node() {
        Set<Node> set = new HashSet<>();
        set.add(new Node(1, 0, "node1"));
        final String jsonStr = Treeify.treeifySet(set);
        final String jsonString = Treeify.treeifySetOptimized(set);
        assertThat("{id:1,parentId:0,code:node1,children:[]}", is(jsonStr));
        assertThat("{id:1,parentId:0,code:node1,children:[]}", is(jsonString));
    }

    @Test
    public void test_two_node() {
        Set<Node> set = new HashSet<>();
        set.add(new Node(1, 0, "node1"));
        set.add(new Node(2, 1, "node2"));
        final String jsonStr = Treeify.treeifySet(set);
        final String jsonString = Treeify.treeifySetOptimized(set);
        assertThat("{id:1,parentId:0,code:node1,children:[{id:2,parentId:1,code:node2,children:[]}]}", is(jsonStr));
        assertThat("{id:1,parentId:0,code:node1,children:[{id:2,parentId:1,code:node2,children:[]}]}", is(jsonString));
    }

    @Test
    public void test_two_node_in_diff_order() {
        Set<Node> set = new HashSet<>();
        set.add(new Node(2, 0, "node1"));
        set.add(new Node(1, 2, "node2"));
        final String jsonStr = Treeify.treeifySet(set);
        final String jsonString = Treeify.treeifySetOptimized(set);
        assertThat("{id:2,parentId:0,code:node1,children:[{id:1,parentId:2,code:node2,children:[]}]}", is(jsonStr));
        assertThat("{id:2,parentId:0,code:node1,children:[{id:1,parentId:2,code:node2,children:[]}]}", is(jsonString));
    }

    @Test
    public void test_three_node() {
        Set<Node> set = new HashSet<>();
        set.add(new Node(1, 0, "node1"));
        set.add(new Node(2, 1, "node2"));
        set.add(new Node(3, 1, "node3"));
        final String jsonStr = Treeify.treeifySet(set);
        final String jsonString = Treeify.treeifySetOptimized(set);
        assertThat("{id:1,parentId:0,code:node1,children:[{id:2,parentId:1,code:node2,children:[]},{id:3,parentId:1,code:node3,children:[]}]}", is(jsonStr));
        assertThat("{id:1,parentId:0,code:node1,children:[{id:2,parentId:1,code:node2,children:[]},{id:3,parentId:1,code:node3,children:[]}]}", is(jsonString));
    }

    @Test
    public void test_three_node_with_diff_pid() {
        Set<Node> set = new HashSet<>();
        set.add(new Node(1, 0, "node1"));
        set.add(new Node(2, 1, "node2"));
        set.add(new Node(3, 2, "node3"));
        final String jsonStr = Treeify.treeifySet(set);
        final String jsonString = Treeify.treeifySetOptimized(set);
        assertThat("{id:1,parentId:0,code:node1,children:[{id:2,parentId:1,code:node2,children:[{id:3,parentId:2,code:node3,children:[]}]}]}", is(jsonStr));
        assertThat("{id:1,parentId:0,code:node1,children:[{id:2,parentId:1,code:node2,children:[{id:3,parentId:2,code:node3,children:[]}]}]}", is(jsonString));
    }

    @Test
    public void test_four_node() {
        Set<Node> set = new HashSet<>();
        set.add(new Node(1, 0, "node1"));
        set.add(new Node(2, 1, "node2"));
        set.add(new Node(3, 1, "node3"));
        set.add(new Node(4, 1, "node4"));
        final String jsonStr = Treeify.treeifySet(set);
        final String jsonString = Treeify.treeifySetOptimized(set);
        assertThat("{id:1,parentId:0,code:node1,children:[{id:2,parentId:1,code:node2,children:[]},{id:3,parentId:1,code:node3,children:[]}," +
                "{id:4,parentId:1,code:node4,children:[]}]}", is(jsonStr));
        assertThat("{id:1,parentId:0,code:node1,children:[{id:2,parentId:1,code:node2,children:[]},{id:3,parentId:1,code:node3,children:[]}," +
                "{id:4,parentId:1,code:node4,children:[]}]}", is(jsonString));
    }

    @Test
    public void test_four_node_with_diff_pid() {
        Set<Node> set = new HashSet<>();
        set.add(new Node(1, 0, "node1"));
        set.add(new Node(2, 1, "node2"));
        set.add(new Node(3, 2, "node3"));
        set.add(new Node(4, 3, "node4"));
        final String jsonStr = Treeify.treeifySet(set);
        final String jsonString = Treeify.treeifySetOptimized(set);
        assertThat("{id:1,parentId:0,code:node1,children:[{id:2,parentId:1,code:node2,children:[{id:3,parentId:2,code:node3,children:" +
                "[{id:4,parentId:3,code:node4,children:[]}]}]}]}", is(jsonStr));
        assertThat("{id:1,parentId:0,code:node1,children:[{id:2,parentId:1,code:node2,children:[{id:3,parentId:2,code:node3,children:" +
                "[{id:4,parentId:3,code:node4,children:[]}]}]}]}", is(jsonString));
    }

    @Test
    public void test_five_node() {
        Set<Node> set = new HashSet<>();
        set.add(new Node(1, 0, "node1"));
        set.add(new Node(2, 1, "node2"));
        set.add(new Node(3, 1, "node3"));
        set.add(new Node(4, 3, "node4"));
        set.add(new Node(5, 3, "node5"));
        final String jsonStr = Treeify.treeifySet(set);
        final String jsonString = Treeify.treeifySetOptimized(set);
        assertThat("{id:1,parentId:0,code:node1,children:[{id:2,parentId:1,code:node2,children:[]},{id:3,parentId:1,code:node3,children:" +
                "[{id:4,parentId:3,code:node4,children:[]},{id:5,parentId:3,code:node5,children:[]}]}]}", is(jsonStr));
        assertThat("{id:1,parentId:0,code:node1,children:[{id:2,parentId:1,code:node2,children:[]},{id:3,parentId:1,code:node3,children:" +
                "[{id:4,parentId:3,code:node4,children:[]},{id:5,parentId:3,code:node5,children:[]}]}]}", is(jsonString));
    }

}