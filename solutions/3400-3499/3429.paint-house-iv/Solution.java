class Solution {
    private long f(int i, int left, int right, int n, int[][] cost, long[][][] dp) {
        
        if(i >= n / 2) return 0;
        if(dp[i][left][right] != -1) return dp[i][left][right];

        long t = Long.MAX_VALUE;
        for(int col1 = 0; col1 < 3; col1++) {
            if(col1 != left) {
                for(int col2 = 0; col2 < 3; col2++) {
                    if(col2 != right && col2 != col1) {
                        long q = cost[i][col1] + cost[n - 1 - i][col2] + f(i + 1, col1, col2, n, cost, dp);
                        t = Math.min(t, q);
                    }
                }
            }
        }
        dp[i][left][right] = t;
        return t;
    }

    public long minCost(int n, int[][] cost) {
        long[][][] dp = new long[n][4][4];
        for (long[][] arr : dp) {
            for (long[] subArr : arr) {
                Arrays.fill(subArr, -1);
            }
        }
        return f(0, 3, 3, n, cost, dp);
    }
}