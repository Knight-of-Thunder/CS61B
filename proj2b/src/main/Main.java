package main;

import Ngodnet.WordNet;
import browser.NgordnetServer;
import org.slf4j.LoggerFactory;
import static utils.Utils.*;

public class Main {
    static {
        LoggerFactory.getLogger(Main.class).info("\033[1;38mChanging text color to white");
    }
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();

        String synsetFile = "./data/wordnet/synsets.txt";
        String hyponymFile = "./data/wordnet/hyponyms.txt";

        hns.startUp();
        hns.register("history", new DummyHistoryHandler());
        hns.register("historytext", new DummyHistoryTextHandler());
        hns.register("hyponyms", new HyponymsHandler(new WordNet(synsetFile, hyponymFile)));

        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}