# Nearest Neighbor Search System

A high-performance spatial search system built in Java that indexes 2D geographic coordinates using a k-d tree and grid-based spatial hashing to perform ultra-fast nearest-neighbor lookups.

## Features
- **2D k-d Tree Implementation:** Efficient spatial partitioning for fast multidimensional search.
- **Branch-and-Bound Pruning:** Recursive search optimization that discards entire subtrees that cannot contain a closer neighbor.
- **Grid-Based Spatial Hashing:** Constant-time deduplication to filter duplicate coordinate points[cite: 1].
- **Performance:** Achieves an approximate 25x query speedup compared to standard brute-force linear search baselines[cite: 1].

## Technologies Used
- **Language:** Java[cite: 1]
- **Core Concepts:** Binary Trees, Spatial Data Structures, Algorithmic Optimization, Recursion
