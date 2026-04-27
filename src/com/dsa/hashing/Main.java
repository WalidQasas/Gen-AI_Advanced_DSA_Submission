package com.dsa.hashing;

/**
 * Main entry point for the Hashing Comparison project.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Advanced Data Structures: Hashing Comparison ===");
        
        // 1. Correctness Test
        runCorrectnessTest();
        
        System.out.println("\n");
        
        // 2. Performance Benchmark
        Benchmark.run();
    }

    private static void runCorrectnessTest() {
        System.out.println("--- Correctness Test ---");
        
        testTable("Linear Probing", new LinearProbingHashTable<>(5));
        System.out.println();
        testTable("Cuckoo Hashing", new CuckooHashTable<>(5));
    }

    private static void testTable(String name, HashTable<Integer, String> table) {
        System.out.println("Testing " + name + "...");
        
        // Insertions
        table.insert(1, "One");
        table.insert(2, "Two");
        table.insert(11, "Eleven"); // Potential collision
        table.insert(21, "Twenty-One"); // Potential collision
        
        System.out.println("  Size after inserts: " + table.size());
        System.out.println("  Search(11): " + table.search(11));
        System.out.println("  Search(5): " + table.search(5) + " (Expected: null)");
        
        // Update
        table.insert(2, "Updated Two");
        System.out.println("  Search(2) after update: " + table.search(2));
        
        // Deletion
        table.delete(11);
        System.out.println("  Search(11) after delete: " + table.search(11) + " (Expected: null)");
        System.out.println("  Size after delete: " + table.size());
        
        // Verify other elements still present
        System.out.println("  Search(21) after 11 deleted: " + table.search(21));
    }
}
