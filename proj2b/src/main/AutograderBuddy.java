//package main;
//
//import browser.NgordnetQueryHandler;
//
//
//public class AutograderBuddy {
//    /** Returns a HyponymHandler */
//    public static NgordnetQueryHandler getHyponymsHandler(
//            String wordFile, String countFile,
//            String synsetFile, String hyponymFile) {
//
//        throw new RuntimeException("Please fill out AutograderBuddy.java!");
//    }
//}
package main;

import Ngodnet.WordNet;
import browser.NgordnetQueryHandler;

public class AutograderBuddy {
    /** Returns a HyponymsHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        // 忽略 wordFile 和 countFile，因为 k = 0 时不需要 NGrams 数据
        WordNet wn = new WordNet(synsetFile, hyponymFile);
        return new HyponymsHandler(wn);
    }
}
