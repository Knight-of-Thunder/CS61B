package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        if(containsKey(key)){
            findNode(key).value = value;
        }
        else {
            size++;
            // 判断是否需要扩容
            if ((double) size / initialCapacity > loadFactor) {
                resize();
            }
            int bucket = Math.floorMod(key.hashCode(), initialCapacity);
            buckets[bucket].add(new Node(key, value));
        }

    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        return findNode(key) == null ? null : findNode(key).value;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        return findNode(key) != null;
    }
    private Node findNode(K key) {
        int bucket = Math.floorMod(key.hashCode(), initialCapacity);
        for (Node node : buckets[bucket]){
            if(node.key.equals(key)){
                return node;
            }
        }
        return null;
    }
//    private int findIdx(K key){
//        int bucket = Math.floorMod(key.hashCode(), initialCapacity);
//        Iterator<Node> it = buckets[bucket].iterator();
//        int i = 0;
//        while (it.hasNext()) {
//            if(it.next().value.equals(key))
//                break;
//            i ++;
//        }
//        if(i == buckets[bucket].size())
//            return -1;
//        return i;
//    }
    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        for (Collection<Node> c : buckets) {
            c.clear();
        }
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    double loadFactor;
    int initialCapacity;
    int size = 0;

    static int defaultInitialCapacity = 16;
    static double defaultInitialLoadFactor = 0.75;

    /** Constructors */
    public MyHashMap() {
        constructHelper(defaultInitialCapacity, defaultInitialLoadFactor);
    }

    public MyHashMap(int initialCapacity) {
        constructHelper(initialCapacity, defaultInitialLoadFactor);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        constructHelper(initialCapacity, loadFactor);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // TODO: Fill in this method.

        return new LinkedList<Node>();
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    private void constructHelper(int initialCapacity, double loadFactor){
        this.loadFactor = loadFactor;
        this.initialCapacity = initialCapacity;
        buckets = new Collection[initialCapacity];
        for(int i = 0; i < initialCapacity; i++){
            buckets[i] = createBucket();
        }
    }

    private void resize() {
        int newCapacity = initialCapacity * 2;
        Collection<Node>[] newBuckets = new Collection[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = createBucket();
        }

        // 重新插入原有的每个节点（rehash）
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                int newBucketIndex = Math.floorMod(node.key.hashCode(), newCapacity);
                newBuckets[newBucketIndex].add(new Node(node.key, node.value));
            }
        }

        // 更新引用和容量
        buckets = newBuckets;
        initialCapacity = newCapacity;
    }

}
