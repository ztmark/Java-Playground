package com.mark.datastructure.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Mark
 * Date  : 16/3/14.
 */
public class Graph {

    public static void main(String[] args) {
        Graph graph = generateAGraph();
        System.out.println(graph);
    }

    public static Graph generateAGraph() {
        Graph graph = new Graph(13);
        graph.addEdge(0, 5);
        graph.addEdge(4, 3);
        graph.addEdge(0, 1);
        graph.addEdge(9, 12);
        graph.addEdge(6, 4);
        graph.addEdge(5, 4);
        graph.addEdge(0, 2);
        graph.addEdge(11, 12);
        graph.addEdge(9, 10);
        graph.addEdge(0, 6);
        graph.addEdge(7, 8);
        graph.addEdge(9, 11);
        graph.addEdge(3, 5);
        return graph;
    }

    private int V;
    private int E;
    private Set<Integer>[] adj;

    public Graph(int v) {
        this.V = v;
        //noinspection unchecked
        this.adj = new Set[v];
        for (int i = 0; i < this.adj.length; i++) {
            this.adj[i] = new HashSet<>();
        }
    }


    public int vertics() {
        return V;
    }

    public int edges() {
        return E;
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adjacencyList(int v) {
        return adj[v];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(V).append(" vertices ");
        sb.append(E).append(" edges\n");
        for (int i = 0; i < V; i++) {
            sb.append(i).append(" : ");
            for (int v : adj[i]) {
                sb.append(v).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
