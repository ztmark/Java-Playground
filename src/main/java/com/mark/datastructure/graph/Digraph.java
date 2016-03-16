package com.mark.datastructure.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Mark
 * Date  : 16/3/16.
 */
public class Digraph {

    public static void main(String[] args) {
        Digraph digraph = GraphBuilder.generateADigraph();
        System.out.println(digraph);
    }



    private int V;
    private int E;
    private Set<Integer>[] adj;


    public Digraph(int v) {
        this.V = v;
        //noinspection unchecked
        this.adj = new Set[v];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new HashSet<>();
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        this.E++;
    }

    public int V() {
        return this.V;
    }

    public int E() {
        return this.E;
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
