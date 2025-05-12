import net.sf.saxon.om.Item;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T>{
    public class Node{
        public Node prev;
        public T item;
        public Node next;

    }
    Node sentinel;
    int size;
    public LinkedListDeque61B(){
        sentinel = new Node();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        Node first = new Node();
        first.prev = sentinel;
        first.next = sentinel.next;
        sentinel.next.prev = first;
        sentinel.next = first;

        first.item = x;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        Node last = new Node();
        last.next = sentinel;
        last.prev = sentinel.prev;
        sentinel.prev.next = last;
        sentinel.prev = last;

        last.item = x;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node curr = sentinel.next;
        while (curr != sentinel){
            returnList.add(curr.item);
            curr = curr.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        Node curr = sentinel.next;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        return curr.item;
    }

    @Override
    public T removeLast() {
        Node curr = sentinel.prev;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        return curr.item;
    }

    @Override
    public T get(int index) {
        if(index > size - 1 || index < 0 || isEmpty()){
            return null;
        }
        Node curr = sentinel.next;
        for(int i = 0; i < index; i++){
            curr = curr.next;
        }
        return curr.item;
    }

    @Override
    public T getRecursive(int index) {
        if(index > size - 1 || index < 0 || isEmpty()){
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    public T getRecursiveHelper(Node node, int index){
        if(index == 0){
            return node.item;
        }
        return getRecursiveHelper(node.next, index - 1);
    }
}
