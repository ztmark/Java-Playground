package com.mark.datastructure.graph;

import java.util.LinkedList;

/**
 * Author: Mark
 * Date  : 16/3/14.
 */
public class DFSPath {

    public static void main(String[] args) {
        Graph graph = Graph.generateAGraph();
        DFSPath path = new DFSPath(graph, 3);
        System.out.println(path.hasPathTo(2));
        System.out.println(path.hasPathTo(7));
        System.out.println(path.hasPathTo(9));
        System.out.println(path.pathTo(2));
    }

    private boolean[] marked;
    private int[] edgeTo;
    private int source;

    public DFSPath(Graph graph, int s) {
        this.source = s;
        marked = new boolean[graph.vertics()];
        edgeTo = new int[graph.vertics()];
        dfs(graph, s);
    }

    private void dfs(Graph graph, int s) {
        marked[s] = true;
        for (int w : graph.adjacencyList(s)) {
            if (!marked[w]) {
                edgeTo[w] = s;
                dfs(graph, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        LinkedList<Integer> path = new LinkedList<>();
        if (!hasPathTo(v)) {
            return path;
        }
        while (v != source) {
            path.addFirst(v);
            v = edgeTo[v];
        }
        path.addFirst(source);
        return path;
    }
}
