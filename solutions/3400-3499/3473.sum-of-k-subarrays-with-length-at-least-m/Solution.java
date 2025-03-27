class Solution {
  static public int maxSum(int[] nums, int k, int m) {
    int n = nums.length;
    Integer[][][] dp = new Integer[2][k + 1][n];
    return solve(0, k, m, 0, nums, dp);
  }

  static int solve(int i, int k, int m, int started, int[] arr, Integer[][][] dp) {
    if (k == 0)
      return 0;
    if (i >= arr.length) {
      if (k == 1 && started == 1)
        return 0;
      return -1000000000;
    }
    if (dp[started][k][i] != null)
      return dp[started][k][i];
    int a = -1000000000, b = -1000000000, c = -1000000000, d = -1000000000, e = -1000000000;
    if (started == 1) {
      a = arr[i] + solve(i + 1, k, m, 1, arr, dp);
      b = solve(i + 1, k - 1, m, 0, arr, dp);
      if (i + m - 1 < arr.length) {
        int sum = 0;
        for (int j = i; j < i + m; j++)
          sum += arr[j];
        c = sum + solve(i + m, k - 1, m, 1, arr, dp);
      }
    } else {
      if (i + m - 1 < arr.length) {
        int sum = 0;
        for (int j = i; j < i + m; j++)
          sum += arr[j];
        d = sum + solve(i + m, k, m, 1, arr, dp);
      }
      e = solve(i + 1, k, m, started, arr, dp);
    }
    int ans = Math.max(a, Math.max(b, Math.max(c, Math.max(d, e))));
    return dp[started][k][i] = ans;
  }
}