class Solution {
  int solve(int[][] q, int i, int target, int k, int[][] dp) {
    if (target == 0)
      return k; // we found a valid sum equal to target so return current index of query.
    if (k >= q.length || target < 0)
      return q.length + 1; // return a larger number to invalidate this flow
    if (dp[target][k] != -1)
      return dp[target][k];
    int res = solve(q, i, target, k + 1, dp); // skip current query val
    if (q[k][0] <= i && i <= q[k][1])
      res = Math.min(res, solve(q, i, target - q[k][2], k + 1, dp)); // pick the val if its range is in the range of
                                                                     // target index
    return dp[target][k] = res;
  }

  public int minZeroArray(int[] nums, int[][] queries) {
    int ans = -1;
    for (int i = 0; i < nums.length; ++i) {
      int[][] dp = new int[nums[i] + 1][queries.length];
      Arrays.stream(dp).forEach(row -> Arrays.fill(row, -1));
      ans = Math.max(ans, solve(queries, i, nums[i], 0, dp));
    }
    return (ans > queries.length) ? -1 : ans;
  }

}