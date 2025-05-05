import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
//	public static int sum(List<Integer> L) {
//        // TODO: Fill in this function.
//        int sum = 0;
//        for(int i = 0; i < L.size(); i++){
//                sum += L.get(i);
//        }
//        return sum;
//
//    }
    // enhance for
    public static int sum(List<Integer> L) {
        // TODO: Fill in this function.
        int sum = 0;
        for(int num : L){
            sum += num;
        }
        return sum;

    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Fill in this function.
        List<Integer> even_list = new ArrayList<>();
        for(int num : L){
            if(num % 2 ==0){
                even_list.add(num);
            }
        }
        return even_list;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Fill in this function.
        List<Integer> common_list = new ArrayList<>();
        for(int n1 : L1){
            for(int n2 : L2){
                if(n1 == n2){
                    common_list.add(n1);
                }
            }
        }
        return common_list;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
        int count = 0;
        for(String word : words){
            if(word.indexOf(c) != -1){
                count += 1;
            }
        }
        return count;
    }
}
