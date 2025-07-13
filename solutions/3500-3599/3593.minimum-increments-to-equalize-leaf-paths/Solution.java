class Solution {
    public int minIncrease(int n, int[][] edges, int[] cost) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++)
            adj.add(new ArrayList<>());
        for (int[] e : edges) {
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
        }

        long[] sum = new long[n];
        sum[0] = cost[0];

        dfs1(0, -1, adj, cost, sum);

        long target = Long.MIN_VALUE;
        for (int u = 0; u < n; u++) {
            boolean isLeaf = (u == 0 ? adj.get(u).size() == 0 : adj.get(u).size() == 1);
            if (isLeaf)
                target = Math.max(target, sum[u]);
        }

        int[] arr = new int[n];
        dfs2(0, -1, adj, sum, target, arr);

        int[] ans = new int[1];
        dfs3(0, -1, adj, arr, ans);
        return ans[0];
    }

    void dfs1(int u, int p, List<List<Integer>> adj, int[] cost, long[] sum) {
        for (int v : adj.get(u)) {
            if (v == p)
                continue;
            sum[v] = sum[u] + cost[v];
            dfs1(v, u, adj, cost, sum);
        }
    }

    int dfs2(int u, int p, List<List<Integer>> adj, long[] sum, long target, int[] arr) {
        boolean isLeaf = true;
        int mini = Integer.MAX_VALUE;
        for (int v : adj.get(u)) {
            if (v == p)
                continue;
            isLeaf = false;
            mini = Math.min(mini, dfs2(v, u, adj, sum, target, arr));
        }
        if (isLeaf)
            mini = (int) (target - sum[u]);
        arr[u] = mini;
        return mini;
    }

    void dfs3(int u, int p, List<List<Integer>> adj, int[] arr, int[] ans) {
        int parent = p == -1 ? 0 : arr[p];
        if (arr[u] > parent)
            ans[0]++;
        for (int v : adj.get(u)) {
            if (v != p)
                dfs3(v, u, adj, arr, ans);
        }
    }
}
