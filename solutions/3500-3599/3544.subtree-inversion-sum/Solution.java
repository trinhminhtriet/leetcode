import java.util.*;

class Solution {
    static final long MOD = (long) 1e9 + 7;
    static final long INF = (long) 7e18;

    public long subtreeInversionSum(int[][] edges, int[] nums, int k) {
        int n = edges.length + 1;

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; ++i)
            adj.add(new ArrayList<>());

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        // dp[sign+1][dist+1][curr]
        long[][][] dp = new long[3][52][n];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 52; j++)
                Arrays.fill(dp[i][j], -INF);

        return find(0, -1, 0, -1, adj, nums, k, dp);
    }

    private long find(int curr, int prev, int sign, int dist,
            List<List<Integer>> adj, int[] nums, int k, long[][][] dp) {

        if (dist > 50)
            dist = 50;

        if (dp[sign + 1][dist + 1][curr] != -INF)
            return dp[sign + 1][dist + 1][curr];

        if (dist == -1) { // no inversions yet
            long invert = -nums[curr];
            long noInvert = nums[curr];

            for (int next : adj.get(curr)) {
                if (next != prev) {
                    invert += find(next, curr, -1, 1, adj, nums, k, dp);
                }
            }

            for (int next : adj.get(curr)) {
                if (next != prev) {
                    noInvert += find(next, curr, 0, -1, adj, nums, k, dp);
                }
            }

            return dp[sign + 1][dist + 1][curr] = Math.max(invert, noInvert);
        }

        if (dist >= k) { // inversion allowed
            long invert = -nums[curr] * sign;
            long noInvert = nums[curr] * sign;

            for (int next : adj.get(curr)) {
                if (next != prev) {
                    invert += find(next, curr, -sign, 1, adj, nums, k, dp);
                }
            }

            for (int next : adj.get(curr)) {
                if (next != prev) {
                    noInvert += find(next, curr, sign, dist + 1, adj, nums, k, dp);
                }
            }

            return dp[sign + 1][dist + 1][curr] = Math.max(invert, noInvert);
        } else { // inversion not allowed
            long sum = nums[curr] * sign;

            for (int next : adj.get(curr)) {
                if (next != prev) {
                    sum += find(next, curr, sign, dist + 1, adj, nums, k, dp);
                }
            }

            return dp[sign + 1][dist + 1][curr] = sum;
        }
    }
}
