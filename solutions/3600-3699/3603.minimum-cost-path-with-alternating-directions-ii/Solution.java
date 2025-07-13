class Solution {
    private static final long INF = (long) 1e15;

    public long minCost(int m, int n, int[][] waitCost) {
        long[][][] dp = new long[m][n][2];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                dp[i][j][0] = dp[i][j][1] = -1L;
        return 1 + f(0, 0, 1, waitCost, dp);
    }

    private long f(int i, int j, int parity, int[][] waitCost, long[][][] dp) {
        int m = waitCost.length;
        int n = waitCost[0].length;
        if (i == m - 1 && j == n - 1)
            return 0;
        if (i < 0 || i >= m || j < 0 || j >= n)
            return INF;
        if (dp[i][j][parity] != -1L)
            return dp[i][j][parity];
        long ans = INF;
        if (parity == 1) {
            ans = Math.min(ans, (long) (i + 2) * (j + 1) + f(i + 1, j, 0, waitCost, dp));
            ans = Math.min(ans, (long) (i + 1) * (j + 2) + f(i, j + 1, 0, waitCost, dp));
        } else {
            ans = Math.min(ans, waitCost[i][j] + f(i, j, 1, waitCost, dp));
        }
        dp[i][j][parity] = ans;
        return ans;
    }
}
