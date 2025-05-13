import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T>{
    T[] items;
    int size;
    int arraysize;
    int nextLast;
    int nextFirst;
    public ArrayDeque61B(){
        nextFirst = 3;
        nextLast = 4;
        arraysize = 8;
        size = 0;
        items = (T [])new Object[arraysize];
    }

    @Override
    public void addFirst(T x) {
        items[Math.floorMod(nextFirst, arraysize)] = x;
        size += 1;
        nextFirst -= 1;

        // resize
        if(size == arraysize){
            T[] tp = (T [])new Object[arraysize * 2];
            for(int i = 0; i < size; i++){
                tp[i] = get(i);
            }
            arraysize *= 2;
            items = tp;
            nextFirst = arraysize - 1;
            nextLast = size;
        }
    }

    @Override
    public void addLast(T x) {
        items[Math.floorMod(nextLast, arraysize)] = x;
        size += 1;
        nextLast += 1;

        //resize
        if(size == arraysize){
            T[] tp = (T [])new Object[arraysize * 2];
            for(int i = 0; i < size; i++){
                tp[i] = get(i);
            }
            arraysize *= 2;
            items = tp;
        }

    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int i = Math.floorMod(nextFirst + 1, arraysize);
        while (i != nextLast){
            returnList.add(items[Math.floorMod(i, arraysize)]);
            i ++;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        T x = items[Math.floorMod(nextFirst + 1, arraysize)];
        size -= 1;
        nextFirst += 1;
        return x;
    }

    @Override
    public T removeLast() {
        T x = items[Math.floorMod(nextLast - 1, arraysize)];
        size -= 1;
        nextLast -= 1;
        return x;
    }

    @Override
    public T get(int index) {
        if(index > size - 1 || index < 0){
            return null;
        }
        return items[Math.floorMod(nextFirst + index + 1, arraysize)];
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}
