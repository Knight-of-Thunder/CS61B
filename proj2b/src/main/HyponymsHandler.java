package main;
import Ngodnet.WordNet;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.HashSet;
import java.util.Set;

public class HyponymsHandler extends NgordnetQueryHandler{
    private WordNet wordNet;
    public HyponymsHandler(WordNet wn){
        wordNet = wn;
    }
    @Override
    public String handle(NgordnetQuery q) {
        Set<String> words = new HashSet<>(q.words());
        Set<String> result = wordNet.hyponyms(words);
        return result.toString();
    }
}
