package main;
import Ngodnet.WordNet;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import ngrams.NGramMap;

import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class HyponymsHandler extends NgordnetQueryHandler{
    private WordNet wordNet;
    private NGramMap nGramMap;
    public HyponymsHandler(WordNet wn, NGramMap ngm){
        wordNet = wn;
        nGramMap = ngm;
    }
    @Override
    public String handle(NgordnetQuery q) {
        Set<String> words = new HashSet<>(q.words());
        Set<String> result = new HashSet<>();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();
        NgordnetQueryType handleType = q.ngordnetQueryType();
        Set<String> topKWords = nGramMap.topKWord(k, words, startYear, endYear);
        if(handleType == NgordnetQueryType.HYPONYMS ) {
            result = wordNet.hyponyms(topKWords);
        }
        else{
            result = wordNet.Ancestors(topKWords);
        }

        return result.toString();
    }
}
