package Ngodnet;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private Map<Integer, List<String>> idToWords;
    private Map<String, Set<Integer>> wordToId;
    private Graph graph;

    public WordNet(String synsetFile, String hyponymFile){
        idToWords = new HashMap<>();
        wordToId = new HashMap<>();
        graph = new Graph();

        loadSynsetFile(synsetFile);
        loadHyponyms(hyponymFile);
    }

    private void loadSynsetFile(String synsetFile){
        In in = new In(synsetFile);
        while (!in.isEmpty()){
            String readline = in.readLine();
            String [] splitLine = readline.split(",");
            int id = Integer.parseInt(splitLine[0]);
            String [] words = splitLine[1].split(" ");
           idToWords.put(id, Arrays.asList(words));
           for(String word: words){
                wordToId.computeIfAbsent(word, k -> new HashSet<>()).add(id);
           }

        }
    }

    private void loadHyponyms(String hyponymFile){
        In in = new In(hyponymFile);
        while (!in.isEmpty()){
            String readline = in.readLine();
            String [] splitLine = readline.split(",");
            int from = Integer.parseInt(splitLine[0]);
            for(int i = 1; i < splitLine.length; i++){
                graph.addEdge(from, Integer.parseInt(splitLine[i]));
            }
        }
    }

    public Set<String> hyponyms(Set<String> words){
        List<Set<Integer>> allDescendants = new ArrayList<>();

        for(String word : words){
            Set<Integer> ids = wordToId.getOrDefault(word, Set.of());
            Set<Integer> descendants = new HashSet<>();
            for(int id : ids){
                descendants.addAll(graph.getDescendants(id));
            }
            allDescendants.add(descendants);
         }
        Set<Integer> common = allDescendants.getFirst();
        for(int i = 1; i < allDescendants.size(); i++){
            common.retainAll(allDescendants.get(i));
        }

        Set<String> result = new TreeSet<>();
        for(int id : common){
            result.addAll(idToWords.getOrDefault(id, List.of()));
        }
        return result;
    }

    public Set<String> Ancestors(Set<String> words){
        List<Set<Integer>> allAncestor = new ArrayList<>();

        for(String word : words){
            Set<Integer> ids = wordToId.getOrDefault(word, Set.of());
            Set<Integer> ancestors = new HashSet<>();
            for(int id : ids){
                ancestors.addAll(graph.getAncestor(id));
            }
            allAncestor.add(ancestors);
        }
        Set<Integer> common = allAncestor.getFirst();
        for(int i = 1; i < allAncestor.size(); i++){
            common.retainAll(allAncestor.get(i));
        }

        Set<String> result = new TreeSet<>();
        for(int id : common){
            result.addAll(idToWords.getOrDefault(id, List.of()));
        }
        return result;
    }
}
