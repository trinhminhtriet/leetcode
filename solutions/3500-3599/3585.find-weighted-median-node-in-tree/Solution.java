import java.util.*;

public class Solution {
    List<int[]>[] adj;
    long[] depth;
    long[] dist;
    int LOG = 20;
    int[][] up;

    public int[] findMedian(int n, int[][] edges, int[][] queries) {
        adj = new ArrayList[n + 1];
        depth = new long[n + 1];
        dist = new long[n + 1];
        up = new int[n + 1][LOG];
        for (int i = 0; i <= n; i++)
            adj[i] = new ArrayList<>();

        for (int[] e : edges) {
            int u = e[0] + 1;
            int v = e[1] + 1;
            int w = e[2];
            adj[u].add(new int[] { v, w });
            adj[v].add(new int[] { u, w });
        }

        depth[1] = 1;
        dfs(1, 0);

        for (int j = 1; j < LOG; j++) {
            for (int i = 1; i <= n; i++) {
                int p = up[i][j - 1];
                up[i][j] = (p != 0) ? up[p][j - 1] : 0;
            }
        }

        int q = queries.length;
        int[] ans = new int[q];
        for (int i = 0; i < q; i++) {
            int u = queries[i][0] + 1;
            int v = queries[i][1] + 1;
            int lca = LCA(u, v);

            long totalW = dist[u] + dist[v] - 2 * dist[lca];
            long tar = (totalW + 1) / 2;
            long len = depth[u] + depth[v] - 2 * depth[lca];

            long low = 0, high = len;
            int x = v;

            while (low <= high) {
                long mid = (low + high) / 2;
                int mNode = getMNode(u, v, (int) mid, lca);
                if (mNode == -1)
                    break;

                long weight = dist[u] + dist[mNode] - 2 * dist[LCA(u, mNode)];
                if (weight >= tar) {
                    x = mNode;
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }

            ans[i] = x - 1;
        }
        return ans;
    }

    void dfs(int node, int par) {
        up[node][0] = par;
        for (int[] e : adj[node]) {
            int nxt = e[0];
            int w = e[1];
            if (nxt != par) {
                depth[nxt] = depth[node] + 1;
                dist[nxt] = dist[node] + w;
                dfs(nxt, node);
            }
        }
    }

    int lift(int node, int k) {
        for (int j = LOG - 1; j >= 0; j--) {
            if (((k >> j) & 1) == 1) {
                node = up[node][j];
                if (node == 0)
                    return -1;
            }
        }
        return node;
    }

    int LCA(int u, int v) {
        if (depth[u] < depth[v]) {
            int tmp = u;
            u = v;
            v = tmp;
        }
        int diff = (int) (depth[u] - depth[v]);
        u = lift(u, diff);
        if (u == v)
            return u;
        for (int j = LOG - 1; j >= 0; j--) {
            if (up[u][j] != 0 && up[u][j] != up[v][j]) {
                u = up[u][j];
                v = up[v][j];
            }
        }
        return up[u][0];
    }

    int getMNode(int u, int v, int k, int lca) {
        int distU = (int) (depth[u] - depth[lca]);
        if (k <= distU) {
            return lift(u, k);
        } else {
            int distV = (int) (depth[v] - depth[lca]);
            int rem = distV - (k - distU);
            if (rem < 0)
                return -1;
            return lift(v, rem);
        }
    }
}
