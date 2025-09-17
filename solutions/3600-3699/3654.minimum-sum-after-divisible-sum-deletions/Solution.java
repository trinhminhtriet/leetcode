class Solution {
    public long minArraySum(int[] A, int k) {
        long[] dp = new long[k];
        Arrays.fill(dp, Long.MAX_VALUE);
        dp[0] = 0;
        long res = 0;
        for (int a : A) {
            res += a;
            int index = (int) (res % k);
            res = dp[index] = Math.min(dp[index], res);
        }
        return res;
    }
}