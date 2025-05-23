package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.*;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    Map<String, TimeSeries> WordCount = new HashMap<>();
    TimeSeries yearCount = new TimeSeries();

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        In in = new In(wordsFilename);
        while (!in.isEmpty()){
            String readline = in.readLine();
            String [] splitLine = readline.split("\t");
            String name = splitLine[0];
            Integer year = Integer.valueOf(splitLine[1]);
            Double count = Double.valueOf(splitLine[2]);
            if(!WordCount.containsKey(name)){
                TimeSeries ts = new TimeSeries();
                WordCount.put(name, ts);
            }
            WordCount.get(name).put(year, count);

        }

        in = new In(countsFilename);
        while (!in.isEmpty()){
            String readline = in.readLine();
            String [] splitLine = readline.split(",");
            Integer year = Integer.valueOf(splitLine[0]);
            Double count = Double.valueOf(splitLine[1]);
            if(!yearCount.containsKey(year)){
                yearCount.put(year, count);
            }else
                yearCount.put(year,yearCount.get(year) + count);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if(!WordCount.containsKey(word))
            return new TimeSeries();
        return new TimeSeries(this.WordCount.get(word), startYear, endYear);
    }
    public double countHistoryAllYears(String word, int startYear, int endYear) {
        TimeSeries ts = countHistory(word, startYear, endYear);
        double sum = 0;
        for(Double count : ts.values()){
            sum += count;
        }
        return sum;
    }
    public Set<String> topKWord(int k, Set<String> words, int startYear, int endYear){
        Map<String, Double> wordCount = new HashMap<>();
        for(String word : words){
            wordCount.put(word, countHistoryAllYears(word, startYear, endYear));
        }
        PriorityQueue<Map.Entry<String, Double>> maxHeap = new PriorityQueue<>((a, b) -> Double.compare(b.getValue(), a.getValue()));
        maxHeap.addAll(wordCount.entrySet());
        Set<String> topKWords = new HashSet<>();
        for(int i = 0; i < k; i++){
            topKWords.add(Objects.requireNonNull(maxHeap.poll()).getKey());
        }
        return topKWords;
    }


    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        if(!WordCount.containsKey(word))
            return new TimeSeries();
        return new TimeSeries(this.WordCount.get(word), MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        return new TimeSeries(yearCount, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries cH = countHistory(word, startYear, endYear);
        TimeSeries totalcH = new TimeSeries(totalCountHistory(), startYear, endYear);
        return cH.dividedBy(totalcH);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        TimeSeries cH = countHistory(word);
        TimeSeries totalcH = totalCountHistory();
        return cH.dividedBy(totalcH);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();
        for(String string : words){
            result = result.plus(weightHistory(string, startYear, endYear));
        }
        return result;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();
        for(String string : words){
            result.plus(weightHistory(string));
        }
        return result;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.

}
