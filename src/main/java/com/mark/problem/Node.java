package com.mark.problem;

import java.io.Serializable;
import java.util.Objects;

/**
 * Author: Mark
 * Date  : 2017/4/27
 *
 * 评测题目: 给定一集合Set<Node> nodes,
 * 其中Node类中id和parentId用于表示其与其他Node对象的父子关系
 * parentId为0的是root节点，
 * 要求，提供一个方法，将上述集合作为入参，返回值为json字符串，格式为树状
 * {id:1,parentId:0,code:"node1",children:[{id:n, parentId:1,code:"noden",children:[...]},{...}]}
 * 没有任何限制，可以使用开源框架实现
 *
 *
 * 一个 node 的 parentId 是否一定比 id 的值要小？
 * children 中是否需要按 id 排序？
 *
 *
 */
public class Node implements Serializable {
    private static final long serialVersionUID = 1980804338365342364L;

    private int id;
    private int parentId;
    private String code;

    public Node() {
    }

    public Node(int id, int parentId, String code) {
        this.id = id;
        this.parentId = parentId;
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id &&
                parentId == node.parentId &&
                Objects.equals(code, node.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentId, code);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
