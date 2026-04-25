package com.dsa.hashing;

public interface HashTable<K, V> {
    void insert(K key, V value);
    V search(K key);
    void delete(K key);
    int size();
    int capacity();
    default double loadFactor() {
        return (double) size() / capacity();
    }
}
