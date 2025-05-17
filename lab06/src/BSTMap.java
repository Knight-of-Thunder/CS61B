import org.apache.commons.lang3.ObjectUtils;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K, V>{
    class Node{
        Node left;
        Node right;
        K key;
        V value;
        public Node(K key, V value){
            left = null;
            right = null;
            this.key = key;
            this.value = value;
        }

    }
    Node root;
    int size;


    public BSTMap(){
        root = null;
        size = 0;
    }



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
        root = insert(root, key, value);
    }
    Node insert(Node node, K key, V value){
        if(node == null){
            size ++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(node.key);
        if(cmp < 0){
            node.left = insert(node.left, key, value);
        }else if(cmp > 0){
            node.right = insert(node.right, key, value);
        }else {
            node.value = value;
        }
        return node;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        Node node = find(root, key);
        return node == null ? null : node.value;
    }
    Node find(Node node, K key){
        if(node == null)
            return null;
        int cmp = key.compareTo(node.key);
        if(cmp < 0){
            return find(node.left, key);
        }else if(cmp > 0){
            return find(node.right, key);
        }else {
            return node;
        }
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {

        return find(root, key) != null ;
    }

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
        root = null;
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Not implemented");

    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Not implemented");

    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Not implemented");

    }

}
