import java.util.*;

class Solution {
    private static final int MOD = (int) 1e9 + 7;
    private int maxDepth = 0;

    private void dfs(int node, int parent, int depth, List<List<Integer>> graph) {
        maxDepth = Math.max(maxDepth, depth);
        for (int neighbor : graph.get(node)) {
            if (neighbor != parent) {
                dfs(neighbor, node, depth + 1, graph);
            }
        }
    }

    private long powerMod(long x, long y, int mod) {
        long result = 1;
        while (y > 0) {
            if ((y & 1) == 1) {
                result = (result * x) % mod;
            }
            x = (x * x) % mod;
            y >>= 1;
        }
        return result;
    }

    public int assignEdgeWeights(int[][] edges) {
        int n = edges.length;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n + 1; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        maxDepth = 0;
        dfs(1, -1, 0, graph);

        return (int) powerMod(2, maxDepth - 1, MOD);
    }
}
