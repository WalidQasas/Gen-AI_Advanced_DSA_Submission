# Advanced Hashing Comparison: Linear Probing vs. Cuckoo Hashing

This project implements and compares two advanced hashing techniques in Java: **Linear Probing** (a standard open addressing strategy) and **Cuckoo Hashing** (a strategy using multiple hash functions and key displacement).

## Project Structure

- `HashTable.java`: Generic interface defining core operations (insert, search, delete, size, capacity, load factor).
- `LinearProbingHashTable.java`: Implementation of a standard hash table using linear probing for collision resolution and dynamic rehashing.
- `CuckooHashTable.java`: Implementation of Cuckoo hashing using two tables, two distinct hash functions, and a "kick-out" mechanism for displacements.
- `Benchmark.java`: Performance testing suite that measures execution time and efficiency metrics using random datasets.
- `Main.java`: Entry point that runs correctness tests and the performance benchmark.

## Features

- **Modular Design**: Each data structure is encapsulated in its own class, implementing a common interface.
- **Correctness Validation**: Includes a test harness to verify that insertion, updates, search, and deletions work correctly even under collision scenarios.
- **Performance Benchmarking**:
  - **Insertion Time**: Measures the cost of building the table.
  - **Search Time**: Measures the speed of retrieving existing keys.
  - **Miss Search Time**: Measures the performance of searching for non-existent keys (where Cuckoo hashing typically shines).
  - **Efficiency Metrics**: Tracks collisions (Linear Probing), displacements (Cuckoo Hashing), and load factors.

## How to Run

1. **Compile**:
   ```bash
   javac src/com/dsa/hashing/*.java -d bin
   ```

2. **Run**:
   ```bash
   java -cp bin com.dsa.hashing.Main
   ```

## Performance Observations

- **Linear Probing**: Generally exhibits fast insertion times due to simpler logic but can suffer from "clustering" as the load factor increases, leading to longer search times in high-collision scenarios.
- **Cuckoo Hashing**: Provides guaranteed $O(1)$ search time (worst-case checking only two locations). However, insertion can be slower due to the potential chain of displacements (kicks) and the need for more frequent rehashing if cycles are detected or the load factor becomes too high.
- **Search Performance**: Cuckoo Hashing typically outperforms Linear Probing for search misses, as it only ever needs to check two specific slots, whereas Linear Probing may continue probing through a cluster.
