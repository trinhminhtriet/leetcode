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
    private int[] tin, tout, parent, depth;
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
        this.depth = new int[n + 1];
        this.rootDistance = new long[n + 1];
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new int[] { v, w });
        adj.get(v).add(new int[] { u, w });
    }

    public void initialize(int root) {
        this.timer = 0;
        dfs(root, 0, 0L);
    }

    private void dfs(int u, int p, long dist) {
        parent[u] = p;
        rootDistance[u] = dist;
        tin[u] = ++timer;
        depth[u] = depth[p] + 1;

        for (int[] edge : adj.get(u)) {
            int v = edge[0], w = edge[1];
            if (v != p) {
                dfs(v, u, dist + w);
            }
        }

        tout[u] = timer;
    }

    public int getInTime(int u) {
        return tin[u];
    }

    public int getOutTime(int u) {
        return tout[u];
    }

    public long getRootDistance(int u) {
        return rootDistance[u];
    }

    public int getParent(int u) {
        return parent[u];
    }

    // New method to get edges for a node
    public List<int[]> getEdges(int u) {
        return adj.get(u);
    }
}

class Solution {
    public int[] treeQueries(int n, int[][] edges, int[][] queries) {
        Tree tree = new Tree(n);

        // Build the tree
        for (int[] edge : edges) {
            tree.addEdge(edge[0], edge[1], edge[2]);
        }
        tree.initialize(1);

        FenwickTree fenwick = new FenwickTree(tree.getOutTime(1));
        int[] upEdgeWeight = new int[n + 1];

        // Initialize upEdgeWeight array using the new getEdges method
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
                // Update operation
                int u = query[1], v = query[2], newWeight = query[3];
                int child = (tree.getParent(u) == v) ? u : v;

                long delta = newWeight - upEdgeWeight[child];
                fenwick.update(tree.getInTime(child), delta);
                fenwick.update(tree.getOutTime(child) + 1, -delta);
                upEdgeWeight[child] = newWeight;
            } else {
                // Query operation
                int u = query[1];
                long currentDistance = tree.getRootDistance(u) + fenwick.query(tree.getInTime(u));
                result.add((int) currentDistance);
            }
        }

        return result.stream().mapToInt(i -> i).toArray();
    }
}
