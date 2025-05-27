import java.util.*;

class Solution {
    private static final int MOD = (int) 1e9 + 7;

    private void dfs(int u, int par, int lvl, int[] levels, List<List<Integer>> graph, int[][] LCA) {
        levels[u] = lvl;
        LCA[u][0] = par;

        for (int v : graph.get(u)) {
            if (v != par) {
                dfs(v, u, lvl + 1, levels, graph, LCA);
            }
        }
    }

    private void init(int n, int maxN, int[][] LCA) {
        for (int i = 1; i <= maxN; i++) {
            for (int j = 0; j < n; j++) {
                if (LCA[j][i - 1] != -1) {
                    int par = LCA[j][i - 1];
                    LCA[j][i] = LCA[par][i - 1];
                }
            }
        }
    }

    private int findLCA(int a, int b, int maxN, int[] levels, int[][] LCA) {
        if (levels[a] > levels[b]) {
            int temp = a;
            a = b;
            b = temp;
        }

        int d = levels[b] - levels[a];
        while (d > 0) {
            int jump = (int) (Math.log(d) / Math.log(2));
            b = LCA[b][jump];
            d -= (1 << jump);
        }

        if (a == b)
            return a;

        for (int i = maxN; i >= 0; i--) {
            if (LCA[a][i] != -1 && LCA[a][i] != LCA[b][i]) {
                a = LCA[a][i];
                b = LCA[b][i];
            }
        }

        return LCA[a][0];
    }

    private int getDistance(int u, int v, int maxN, int[] levels, int[][] LCA) {
        int lca = findLCA(u, v, maxN, levels, LCA);
        return levels[u] + levels[v] - 2 * levels[lca];
    }

    private int powerMod(long x, int y, int mod) {
        long result = 1;
        while (y > 0) {
            if ((y & 1) == 1) {
                result = (result * x) % mod;
            }
            x = (x * x) % mod;
            y >>= 1;
        }
        return (int) result;
    }

    public int[] assignEdgeWeights(int[][] edges, int[][] queries) {
        int n = edges.length + 1;
        int m = queries.length;

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0] - 1;
            int v = edge[1] - 1;
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        int maxN = (int) (Math.log(n) / Math.log(2));
        int[] levels = new int[n];
        int[][] LCA = new int[n][maxN + 1];
        for (int[] row : LCA) {
            Arrays.fill(row, -1);
        }

        dfs(0, -1, 0, levels, graph, LCA);
        init(n, maxN, LCA);

        int[] ans = new int[m];
        for (int i = 0; i < m; i++) {
            int u = queries[i][0] - 1;
            int v = queries[i][1] - 1;
            int dist = getDistance(u, v, maxN, levels, LCA);

            if (dist == 0) {
                ans[i] = 0;
            } else {
                ans[i] = powerMod(2, dist - 1, MOD);
            }
        }

        return ans;
    }
}
