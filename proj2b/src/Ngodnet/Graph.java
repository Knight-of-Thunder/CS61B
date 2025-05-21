package Ngodnet;

import java.util.*;

public class Graph {
    private Map<Integer, Set<Integer>> adjList;
    public Graph(){
        adjList = new HashMap<>();
    }
    public void addEdge(int from, int to){
        adjList.computeIfAbsent(from, k -> new HashSet<>()).add(to);
    }

    public void dfs(int current, Set<Integer> visited){
        if(!visited.add(current))return;
        for(Integer neighbor : adjList.getOrDefault(current, Set.of())){
            dfs(neighbor, visited);
        }
    }

    public Set<Integer> getDescendants(int id){
        Set<Integer> visited = new HashSet<>();
        dfs(id, visited);
        return visited;
    }
}
