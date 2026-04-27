package com.dsa.hashing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Benchmark class to compare Linear Probing and Cuckoo Hashing.
 */
public class Benchmark {
    private static final int DATASET_SIZE = 100000;
    private static final Random random = new Random();

    public static void run() {
        System.out.println("Starting Benchmark for " + DATASET_SIZE + " elements...");
        System.out.println("---------------------------------------------------------");

        List<Integer> keys = new ArrayList<>(DATASET_SIZE);
        for (int i = 0; i < DATASET_SIZE; i++) {
            keys.add(random.nextInt(DATASET_SIZE * 10));
        }

        benchmarkHashTable("Linear Probing Hash Table", new LinearProbingHashTable<>(16), keys);
        System.out.println();
        benchmarkHashTable("Cuckoo Hash Table", new CuckooHashTable<>(16), keys);
    }

    private static void benchmarkHashTable(String name, HashTable<Integer, String> table, List<Integer> keys) {
        System.out.println("Benchmarking: " + name);

        // Insertion Benchmark
        long startTime = System.nanoTime();
        for (Integer key : keys) {
            table.insert(key, "Value-" + key);
        }
        long endTime = System.nanoTime();
        double insertionTime = (endTime - startTime) / 1_000_000.0;

        // Search Hit Benchmark
        startTime = System.nanoTime();
        int foundCount = 0;
        for (Integer key : keys) {
            if (table.search(key) != null) {
                foundCount++;
            }
        }
        endTime = System.nanoTime();
        double searchHitTime = (endTime - startTime) / 1_000_000.0;

        // Search Miss Benchmark
        startTime = System.nanoTime();
        int missCount = 0;
        // Search in range count*20 to count*21 as requested
        for (int i = DATASET_SIZE * 20; i < DATASET_SIZE * 21; i++) {
            table.search(i);
            missCount++;
        }
        endTime = System.nanoTime();
        double searchMissTime = (endTime - startTime) / 1_000_000.0;

        // Results
        System.out.printf("  Total Size:          %d\n", table.size());
        System.out.printf("  Capacity:            %d\n", table.capacity());
        System.out.printf("  Load Factor:         %.2f\n", table.loadFactor());
        System.out.printf("  Insertion Time:      %.2f ms\n", insertionTime);
        System.out.printf("  Search Hit Time:     %.2f ms (for %d elements)\n", searchHitTime, foundCount);
        System.out.printf("  Search Miss Time:    %.2f ms (for %d misses)\n", searchMissTime, missCount);

        if (table instanceof LinearProbingHashTable) {
            System.out.printf("  Total Collisions:    %d\n", ((LinearProbingHashTable<?, ?>) table).getCollisions());
        } else if (table instanceof CuckooHashTable) {
            System.out.printf("  Total Displacements: %d\n", ((CuckooHashTable<?, ?>) table).getDisplacements());
        }
    }
}
