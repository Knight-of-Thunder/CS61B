package deque;

import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T>{
    Comparator<T> cp;
    public MaxArrayDeque61B(Comparator<T> c){
        cp = c;
    }
    public T max(){
        int max_indx = 0;
        for(int i = 0; i < size; i++){
            int cmp = cp.compare(get(i), get(max_indx));
            if(cmp > 0){
                max_indx = i;
            }
        }
        return get(max_indx);
    }
    public T max(Comparator<T> cp){
        int max_indx = 0;
        for(int i = 0; i < size; i++){
            int cmp = cp.compare(get(i), get(max_indx));
            if(cmp > 0){
                max_indx = i;
            }
        }
        return get(max_indx);
    }
    // 以下内容可以实现可以不实现
//    private class NaturalComparator implements Comparator<Integer>{
//        @Override
//        public int compare(Integer o1, Integer o2) {
//            return 0;
//        }
//    }
//    public Comparator<Integer> getNaturalComparator(){
//        return new NaturalComparator();
//    }


}
