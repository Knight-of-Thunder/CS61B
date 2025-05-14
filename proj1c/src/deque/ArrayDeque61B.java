package deque;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
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

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    private class ArrayDequeIterator implements Iterator<T>{
        private int index;
        public ArrayDequeIterator(){
            index = 0;
        }
        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            index += 1;
            return get(index - 1);
        }

    }
    @Override
    public boolean equals(Object other){
        if(this == other){
            return true;
        }
        if(other == null){
            return false;
        }
        if(!(other instanceof ArrayDeque61B<?>)){
            return false;
        }
        ArrayDeque61B<T> o = (ArrayDeque61B<T>) other;
        if(o.size() != this.size()){
            return false;
        }
        for(int i = 0; i < size; i++){
            if(o.get(i) != this.get(i)){
                return false;
            }
        }
        return true;
    }
    @Override
    public String toString(){
        return toList().toString();
    }

}
