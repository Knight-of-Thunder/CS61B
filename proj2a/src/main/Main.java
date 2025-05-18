package main;

import static utils.Utils.*;

import ngrams.NGramMap;
import org.slf4j.LoggerFactory;

import browser.NgordnetServer;

public class Main {
    static {
        LoggerFactory.getLogger(Main.class).info("\033[1;38mChanging text color to white");
    }
    /* Do not delete or modify the code above! */

    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();

        /* The following code might be useful to you.

        NGramMap ngm = new NGramMap(SHORT_WORDS_FILE, TOTAL_COUNTS_FILE);

        */

        hns.startUp();
        hns.register("history", new HistoryHandler(new NGramMap(TOP_14337_WORDS_FILE,
                TOTAL_COUNTS_FILE)));
        hns.register("historytext", new HistoryTextHandler(new NGramMap(TOP_14337_WORDS_FILE,
                TOTAL_COUNTS_FILE)));

        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet_2a.html");
    }
}
