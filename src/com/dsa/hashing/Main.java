package com.dsa.hashing;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Correctness Test ---");
        HashTable<Integer, String> lp = new LinearProbingHashTable<>(10);
        lp.insert(1, "A");
        System.out.println("Linear Search(1): " + lp.search(1));
        
        System.out.println("\n--- Benchmark (100k elements) ---");
        Benchmark.run(100000);
    }
}
