class Solution {
    public int maxWeight(int n, int[][] edges, int k, int t) {
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++)
            adj.add(new ArrayList<>());
        for (int[] e : edges)
            adj.get(e[0]).add(new int[] { e[1], e[2] });

        List<Set<Integer>>[] dp = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            dp[i] = new ArrayList<>();
            for (int j = 0; j <= k; j++)
                dp[i].add(new HashSet<>());
            dp[i].get(0).add(0);
        }

        for (int e = 0; e < k; e++) {
            for (int u = 0; u < n; u++) {
                for (int[] edge : adj.get(u)) {
                    int v = edge[0], wt = edge[1];
                    for (int w : dp[u].get(e)) {
                        int new_w = w + wt;
                        if (new_w < t) {
                            dp[v].get(e + 1).add(new_w);
                        }
                    }
                }
            }
        }

        int ans = -1;
        for (int u = 0; u < n; u++) {
            if (!dp[u].get(k).isEmpty()) {
                ans = Math.max(ans, Collections.max(dp[u].get(k)));
            }
        }
        return ans;
    }
}
