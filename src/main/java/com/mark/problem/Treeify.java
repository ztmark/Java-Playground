package com.mark.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Author: Mark
 * Date  : 2017/4/27
 *
 * 这里认为一个节点的 parentId 是比 id 的值小的
 *
 */
public final class Treeify {

    public static String treeifySetOptimizedUseMap(Set<Node> nodes) {
        if (nodes == null || nodes.size() == 0) {
            return "";
        }
        final Map<Integer, Node> nodeMap = nodes.stream().collect(Collectors.toMap(Node::getId, Function.identity()));
        Map<Node, Set<Node>> tree = new TreeMap<>(Comparator.comparing(Node::getParentId).thenComparing(Node::getId));
        for (Node node : nodes) {
            tree.putIfAbsent(node, new HashSet<>());
            final Node parent = nodeMap.get(node.getParentId());
            if (parent != null) { // 不是root节点，需要将节点加入到父节点中
                final Set<Node> children = tree.putIfAbsent(parent, new HashSet<>());
                children.add(node);
            }
        }
        return convertToString(tree);
    }

    private static String convertToString(Map<Node, Set<Node>> tree) {
        StringBuilder sb = new StringBuilder();
        return null;
    }


    /**
     *
     * 对 treeifySet 做的一个优化
     * 通过一个 id -> Tree 的 Map来查找父节点
     *
     * 时间复杂度为 O(nlogn) n 是 nodes.size(), 主要是前面的排序
     *
     * @param nodes
     * @return
     */
    public static String treeifySetOptimized(Set<Node> nodes) {
        if (nodes == null || nodes.size() == 0) {
            return "";
        }
        final Node[] nodeArray = nodes.toArray(new Node[nodes.size()]);
        Arrays.sort(nodeArray, Comparator.comparing(Node::getParentId).thenComparing(Node::getId)); // 根据 parentId 排序，parentId 相同的根据 id 排序

        final Node rootNode = nodeArray[0];
        Tree tree = new Tree(rootNode); // 第一个 parentId 最小为0，肯定是 root
        // id -> TreeNode 的 Map
        Map<Integer, Tree> treeNodeMap = new HashMap<>((int) ((float) nodes.size() / 0.75F + 1.0F)); // 防止扩容
        treeNodeMap.putIfAbsent(rootNode.getId(), tree);

        for (int i = 1; i < nodeArray.length; i++) {
            final Tree treeNode = makeATree(nodeArray[i]);
            treeNodeMap.putIfAbsent(treeNode.root.getId(), treeNode);
            addToTree(treeNodeMap, treeNode);
        }
        return tree.toString();
    }


    /**
     * 该方法将节点按照 parentId 升序排序
     * 第一个节点的 parentId 一定是最小的为0，这个节点为 root 节点
     * 然后遍历之后的节点，将节点添加到 Tree 中
     * 最后调用 Tree 的 toString 方法转成 JSON 字符串
     *
     * 这个方法的时间复杂度为 O(n^2), n 是 nodes.size() 每一个节点的插入都需要通过 BFS 来查父节点
     *
     *
     * @param nodes
     * @return
     */
    @Deprecated
    public static String treeifySet(Set<Node> nodes) {
        if (nodes == null || nodes.size() == 0) {
            return "";
        }

        final Node[] nodeArray = nodes.toArray(new Node[nodes.size()]);
        Arrays.sort(nodeArray, Comparator.comparing(Node::getParentId).thenComparing(Node::getId)); // 根据 parentId 排序，parentId 相同的根据 id 排序

        Tree tree = new Tree(nodeArray[0]); // 第一个 parentId 最小为0，肯定是 root
        // 将之后的节点依次加入到 Tree 中
        for (int i = 1; i < nodeArray.length; i++) {
            addToTree(tree, nodeArray[i]);
        }
        return tree.toString();
    }

    private static void addToTree(Tree tree, Node node) {
        final Optional<Tree> parent = findParentByBFS(tree, node);
        parent.ifPresent(p -> p.children.add(makeATree(node)));
    }

    private static void addToTree(Map<Integer,Tree> treeMap, Tree treeNode) {
        final Optional<Tree> parent = findParentByMap(treeMap, treeNode.root);
        parent.ifPresent(p -> p.children.add(treeNode));
    }

    /**
     * 通过一个 id -> Tree 的 Map来查找父节点
     * @param treeMap
     * @param node
     * @return
     */
    private static Optional<Tree> findParentByMap(Map<Integer, Tree> treeMap, Node node) {
        return Optional.ofNullable(treeMap.get(node.getParentId()));
    }

    /**
     * 通过 BFS 来查找父节点
     * @param tree
     * @param node
     * @return
     */
    private static Optional<Tree> findParentByBFS(Tree tree, Node node) {
        Queue<Tree> queue = new LinkedList<>();
        queue.offer(tree);
        while (!queue.isEmpty()) {
            final Tree tmp = queue.poll();
            if (tmp != null) {
                if (tmp.root.getId() == node.getParentId()) {
                    return Optional.of(tmp);
                }
                queue.addAll(tmp.children);
            }
        }
        return Optional.empty();
    }

    private static Tree makeATree(Node node) {
        return new Tree(node);
    }

    private static class Tree {
        private Node root;
        private List<Tree> children;

        Tree(Node root) {
            this.root = root;
            children = new ArrayList<>();
        }

        @Override
        public String toString() {
            if (root == null) {
                return "";
            }
            final StringBuilder sb = new StringBuilder();
            sb.append("{id:").append(root.getId()).append(",parentId:").append(root.getParentId())
              .append(",code:").append(root.getCode()).append(",children:[");
            final Optional<String> str = children.stream().map(Tree::toString).reduce((t1, t2) -> t1 + "," + t2);
//            final List<String> childrenStrs = children.stream().map(Tree::toString).collect(Collectors.toList());
//            final String string = String.join(",", childrenStrs);
            sb.append(str.orElse(""));
            sb.append("]}");
            return sb.toString();
        }
    }

}
