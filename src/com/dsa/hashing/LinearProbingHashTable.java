package com.dsa.hashing;

public class LinearProbingHashTable<K, V> implements HashTable<K, V> {
    private K[] keys;
    private V[] values;
    private boolean[] occupied;
    private int size, capacity, collisions;

    @SuppressWarnings("unchecked")
    public LinearProbingHashTable(int capacity) {
        this.capacity = capacity;
        this.keys = (K[]) new Object[capacity];
        this.values = (V[]) new Object[capacity];
        this.occupied = new boolean[capacity];
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    @Override
    public void insert(K key, V value) {
        if ((double) size / capacity >= 0.75) rehash();
        int i = hash(key);
        while (occupied[i]) {
            if (keys[i].equals(key)) {
                values[i] = value;
                return;
            }
            i = (i + 1) % capacity;
            collisions++;
        }
        keys[i] = key;
        values[i] = value;
        occupied[i] = true;
        size++;
    }

    @Override
    public V search(K key) {
        int i = hash(key);
        while (occupied[i]) {
            if (keys[i] != null && keys[i].equals(key)) return values[i];
            i = (i + 1) % capacity;
        }
        return null;
    }

    @Override
    public void delete(K key) {
        int i = hash(key);
        while (occupied[i]) {
            if (keys[i] != null && keys[i].equals(key)) {
                keys[i] = null;
                values[i] = null;
                occupied[i] = false;
                reorganize(i);
                size--;
                return;
            }
            i = (i + 1) % capacity;
        }
    }

    private void reorganize(int deletedIdx) {
        int i = (deletedIdx + 1) % capacity;
        while (occupied[i]) {
            K k = keys[i]; V v = values[i];
            occupied[i] = false; keys[i] = null; values[i] = null;
            size--;
            insert(k, v);
            i = (i + 1) % capacity;
        }
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        K[] oldK = keys; V[] oldV = values; boolean[] oldO = occupied;
        capacity *= 2;
        keys = (K[]) new Object[capacity];
        values = (V[]) new Object[capacity];
        occupied = new boolean[capacity];
        size = 0;
        for (int i = 0; i < oldK.length; i++) {
            if (oldO[i] && oldK[i] != null) insert(oldK[i], oldV[i]);
        }
    }

    public int size() { return size; }
    public int capacity() { return capacity; }
    public int getCollisions() { return collisions; }
}
