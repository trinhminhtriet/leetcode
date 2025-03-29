class Solution {
  public long minimumCost(int[] nums, int[] cost, int k) {
      int n = nums.length;
      long[] prefixNums = new long[n + 1];
      long[] prefixCost = new long[n + 1];

      for (int i = 0; i < n; i++) {
          prefixNums[i + 1] = prefixNums[i] + nums[i];
          prefixCost[i + 1] = prefixCost[i] + cost[i];
      }

      long[][] dp = new long[n + 1][n + 1];
      for (int i = 0; i <= n; i++) {
          Arrays.fill(dp[i], Long.MAX_VALUE / 2);
      }
      dp[0][0] = 0;

      for (int i = 1; i <= n; i++) {
          for (int j = 1; j <= i; j++) {
              for (int m = j - 1; m < i; m++) {
                  long sumNums = prefixNums[i] - prefixNums[m];
                  long sumCost = prefixCost[i] - prefixCost[m];
                  long currentCost = (prefixNums[i] + k * j) * sumCost;
                  if (dp[m][j - 1] + currentCost < dp[i][j]) {
                      dp[i][j] = dp[m][j - 1] + currentCost;
                  }
              }
          }
      }

      long minCost = Long.MAX_VALUE;
      for (int j = 1; j <= n; j++) {
          if (dp[n][j] < minCost) {
              minCost = dp[n][j];
          }
      }
      return minCost;
  }
}
