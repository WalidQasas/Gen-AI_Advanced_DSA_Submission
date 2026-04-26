package com.dsa.hashing;
import java.util.*;

public class Benchmark {
    public static void run(int count) {
        List<Integer> keys = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < count; i++) keys.add(r.nextInt(count * 10));

        test("Linear Probing", new LinearProbingHashTable<>(16), keys);
        test("Cuckoo Hashing", new CuckooHashTable<>(16), keys);
    }

    private static void test(String name, HashTable<Integer, String> table, List<Integer> keys) {
        long start = System.nanoTime();
        for (int k : keys) table.insert(k, "V" + k);
        double insertTime = (System.nanoTime() - start) / 1e6;

        start = System.nanoTime();
        for (int k : keys) table.search(k);
        double searchTime = (System.nanoTime() - start) / 1e6;

        System.out.printf("%s -> Insert: %.2fms, Search: %.2fms, Load: %.2f\n", 
            name, insertTime, searchTime, table.loadFactor());
    }
}
