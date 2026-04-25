package com.dsa.hashing;

public class CuckooHashTable<K, V> implements HashTable<K, V> {
    private K[] table1, table2;
    private V[] value1, value2;
    private int size, capacity, displacements;
    private static final int MAX_KICKS = 50;

    @SuppressWarnings("unchecked")
    public CuckooHashTable(int capacity) {
        this.capacity = capacity / 2;
        table1 = (K[]) new Object[this.capacity];
        value1 = (V[]) new Object[this.capacity];
        table2 = (K[]) new Object[this.capacity];
        value2 = (V[]) new Object[this.capacity];
    }

    private int hash1(K key) { return (key.hashCode() & 0x7fffffff) % capacity; }
    private int hash2(K key) {
        int h = key.hashCode();
        h ^= (h >>> 16); h *= 0x85ebca6b; h ^= (h >>> 13);
        return (h & 0x7fffffff) % capacity;
    }

    @Override
    public void insert(K key, V value) {
        if (search(key) != null) { update(key, value); return; }
        if (size >= capacity) rehash();
        
        K curK = key; V curV = value;
        for (int i = 0; i < MAX_KICKS; i++) {
            int h1 = hash1(curK);
            if (table1[h1] == null) { table1[h1] = curK; value1[h1] = curV; size++; return; }
            K tempK = table1[h1]; V tempV = value1[h1];
            table1[h1] = curK; value1[h1] = curV;
            curK = tempK; curV = tempV; displacements++;

            int h2 = hash2(curK);
            if (table2[h2] == null) { table2[h2] = curK; value2[h2] = curV; size++; return; }
            tempK = table2[h2]; tempV = value2[h2];
            table2[h2] = curK; value2[h2] = curV;
            curK = tempK; curV = tempV; displacements++;
        }
        rehash(); insert(curK, curV);
    }

    private void update(K key, V value) {
        int h1 = hash1(key);
        if (table1[h1] != null && table1[h1].equals(key)) { value1[h1] = value; return; }
        int h2 = hash2(key);
        if (table2[h2] != null && table2[h2].equals(key)) value2[h2] = value;
    }

    @Override
    public V search(K key) {
        int h1 = hash1(key), h2 = hash2(key);
        if (table1[h1] != null && table1[h1].equals(key)) return value1[h1];
        if (table2[h2] != null && table2[h2].equals(key)) return value2[h2];
        return null;
    }

    @Override
    public void delete(K key) {
        int h1 = hash1(key), h2 = hash2(key);
        if (table1[h1] != null && table1[h1].equals(key)) { table1[h1] = null; value1[h1] = null; size--; }
        else if (table2[h2] != null && table2[h2].equals(key)) { table2[h2] = null; value2[h2] = null; size--; }
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        K[] oldT1 = table1, oldT2 = table2; V[] oldV1 = value1, oldV2 = value2;
        capacity *= 2; size = 0;
        table1 = (K[]) new Object[capacity]; value1 = (V[]) new Object[capacity];
        table2 = (K[]) new Object[capacity]; value2 = (V[]) new Object[capacity];
        for (int i = 0; i < oldT1.length; i++) {
            if (oldT1[i] != null) insert(oldT1[i], oldV1[i]);
            if (oldT2[i] != null) insert(oldT2[i], oldV2[i]);
        }
    }

    public int size() { return size; }
    public int capacity() { return capacity * 2; }
    public int getDisplacements() { return displacements; }
}
