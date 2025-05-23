package Ngodnet;

import java.util.*;

public class Graph {
    private Map<Integer, Set<Integer>> adjList;
    private Map<Integer, Set<Integer>> reversedAdjList;
    public Graph(){
        adjList = new HashMap<>();
        reversedAdjList = new HashMap<>();
    }
    public void addEdge(int from, int to){
        adjList.computeIfAbsent(from, k -> new HashSet<>()).add(to);
        reversedAdjList.computeIfAbsent(to, k -> new HashSet<>()).add(from);
    }

    public void dfs(int current, Set<Integer> visited){
        if(!visited.add(current))return;
        for(Integer neighbor : adjList.getOrDefault(current, Set.of())){
            dfs(neighbor, visited);
        }
    }
    public void dfsReverse(int current, Set<Integer> visited){
        if(!visited.add(current))return;
        for(Integer neighbor : reversedAdjList.getOrDefault(current, Set.of())){
            dfsReverse(neighbor, visited);
        }
    }

    public Set<Integer> getDescendants(int id){
        Set<Integer> visited = new HashSet<>();
        dfs(id, visited);
        return visited;
    }

    public Set<Integer> getAncestor(int id){
        Set<Integer> visited = new HashSet<>();
        dfsReverse(id, visited);
        return visited;
    }
}
