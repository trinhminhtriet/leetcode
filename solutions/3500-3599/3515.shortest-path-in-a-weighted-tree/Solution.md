# **Intuition**

The problem involves managing a tree structure where we need to handle two types of operations efficiently:

1. **Update** the weight of an edge between two nodes.
2. **Query** the sum of edge weights from the root to a given node.

The key insight is that each query requires the sum of weights along a path from the root to a node, which can be efficiently managed using **Euler Tour (In-Time and Out-Time)** and a **Fenwick Tree (Binary Indexed Tree)**. This allows us to perform both updates and queries in logarithmic time.

# **Approach**

1. **Tree Representation**:

   - Represent the tree using an adjacency list, storing edges with their weights.
   - Perform a **DFS traversal** to compute:
     - **In-Time (`tin`) and Out-Time (`tout`)**: Used to represent the subtree of each node as a range in a linear array.
     - **Parent and Depth**: Track parent-child relationships and node depths.
     - **Root Distance (`rootD`)**: The sum of edge weights from the root to each node.

2. **Fenwick Tree (BIT)**:

   - Used to efficiently handle **range updates** (adding a delta to a subtree) and **point queries** (getting the sum up to a point).
   - When an edge weight is updated, the change propagates to all nodes in the subtree of the child node connected to that edge.

3. **Handling Queries**:
   - **Update Query (`1 u v c`)**:
     - Identify the child node (`c`) of the edge being updated.
     - Compute the delta (`new_weight - old_weight`).
     - Apply this delta to the Fenwick Tree over the range `[tin[c], tout[c]]` using **range update** (add delta at `tin[c]` and subtract at `tout[c] + 1`).
   - **Sum Query (`2 u`)**:
     - The result is the original root distance (`rootD[u]`) plus the sum of all updates affecting the path from the root to `u` (queried using `FenwickTree.query(tin[u])`).

# **Complexity**

- **Tree Construction (DFS)**:

  - **Time**: `O(N)` (each node and edge is processed once).
  - **Space**: `O(N)` for adjacency list, parent, depth, in-time, out-time, and root distance arrays.

- **Fenwick Tree Operations**:

  - **Update**: `O(log N)` per operation (due to BIT properties).
  - **Query**: `O(log N)` per operation.

- **Overall for `Q` Queries**:
  - **Time**: `O(N + Q log N)` (DFS + each query/update in logarithmic time).
  - **Space**: `O(N)` (storage for tree and Fenwick Tree).

### **Solution Code**

```java
import java.util.*;

class FenwickTree {
    private int size;
    private long[] tree;

    public FenwickTree(int size) {
        this.size = size;
        this.tree = new long[size + 1];
    }

    public void update(int index, long delta) {
        for (; index <= size; index += index & -index) {
            tree[index] += delta;
        }
    }

    public long query(int index) {
        long sum = 0;
        for (; index > 0; index -= index & -index) {
            sum += tree[index];
        }
        return sum;
    }
}

class Tree {
    private int n;
    private List<List<int[]>> adj;
    private int[] tin, tout, parent;
    private long[] rootDistance;
    private int timer;

    public Tree(int n) {
        this.n = n;
        this.adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }
        this.tin = new int[n + 1];
        this.tout = new int[n + 1];
        this.parent = new int[n + 1];
        this.rootDistance = new long[n + 1];
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new int[]{v, w});
        adj.get(v).add(new int[]{u, w});
    }

    public void initialize(int root) {
        this.timer = 0;
        dfs(root, 0, 0L);
    }

    private void dfs(int u, int p, long dist) {
        parent[u] = p;
        rootDistance[u] = dist;
        tin[u] = ++timer;

        for (int[] edge : adj.get(u)) {
            int v = edge[0], w = edge[1];
            if (v != p) {
                dfs(v, u, dist + w);
            }
        }

        tout[u] = timer;
    }

    public int getInTime(int u) { return tin[u]; }
    public int getOutTime(int u) { return tout[u]; }
    public long getRootDistance(int u) { return rootDistance[u]; }
    public int getParent(int u) { return parent[u]; }
    public List<int[]> getEdges(int u) { return adj.get(u); }
}

class Solution {
    public int[] treeQueries(int n, int[][] edges, int[][] queries) {
        Tree tree = new Tree(n);

        for (int[] edge : edges) {
            tree.addEdge(edge[0], edge[1], edge[2]);
        }
        tree.initialize(1);

        FenwickTree fenwick = new FenwickTree(n);
        int[] upEdgeWeight = new int[n + 1];

        for (int u = 2; u <= n; u++) {
            for (int[] edge : tree.getEdges(u)) {
                if (edge[0] == tree.getParent(u)) {
                    upEdgeWeight[u] = edge[1];
                    break;
                }
            }
        }

        List<Integer> result = new ArrayList<>();

        for (int[] query : queries) {
            if (query[0] == 1) {
                int u = query[1], v = query[2], newWeight = query[3];
                int child = (tree.getParent(u) == v) ? u : v;

                long delta = newWeight - upEdgeWeight[child];
                fenwick.update(tree.getInTime(child), delta);
                fenwick.update(tree.getOutTime(child) + 1, -delta);
                upEdgeWeight[child] = newWeight;
            } else {
                int u = query[1];
                long currentDistance = tree.getRootDistance(u) + fenwick.query(tree.getInTime(u));
                result.add((int) currentDistance);
            }
        }

        return result.stream().mapToInt(i -> i).toArray();
    }
}
```

### **Explanation**

1. **Tree Initialization**:

   - The tree is built using an adjacency list, and DFS computes `tin`, `tout`, parent pointers, and root distances.

2. **Fenwick Tree**:

   - Used to apply **range updates** (when edge weights change) and **point queries** (to get the sum of updates along a path).

3. **Handling Queries**:
   - **Update Query**: Adjusts the Fenwick Tree to reflect the new edge weight, affecting all nodes in the subtree.
   - **Sum Query**: Combines the original root distance with the sum of updates from the Fenwick Tree to get the current distance.

This approach efficiently handles dynamic updates and queries in logarithmic time per operation, leveraging Euler Tour and Fenwick Tree for optimal performance.
