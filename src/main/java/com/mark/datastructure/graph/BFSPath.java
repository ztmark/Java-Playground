package com.mark.datastructure.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Author: Mark
 * Date  : 16/3/14.
 */
public class BFSPath {

    public static void main(String[] args) {
        Graph graph = GraphBuilder.generateAGraph();
        BFSPath path = new BFSPath(graph, 3);
        System.out.println(path.hasPathTo(2));
        System.out.println(path.hasPathTo(8));
        System.out.println(path.hasPathTo(10));
        System.out.println(path.pathTo(2));
    }

    private boolean[] marked;
    private int[] edgeTo;
    private int source;

    public BFSPath(Graph graph, int s) {
        this.source = s;
        marked = new boolean[graph.vertics()];
        edgeTo = new int[graph.vertics()];
        bfs(graph, s);
    }

    private void bfs(Graph graph, int s) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);
        marked[s] = true;
        int v;
        while (!queue.isEmpty()) {
             v = queue.poll();
            for (int w : graph.adjacencyList(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    queue.offer(w);
                }
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
            path.push(v);
            v = edgeTo[v];
        }
        path.push(source);
        return path;
    }

}
