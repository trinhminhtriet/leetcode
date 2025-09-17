class Solution {
    private static final int MOD = 1000000007;
    public int xorAfterQueries(int[] nums, int[][] q) {
        for (int[] query : q) {
            int l = query[0];
            int r = query[1];
            int k = query[2];
            int v = query[3];
            while (l <= r) {
                nums[l] = (int)(((long)nums[l] * v) % MOD);
                l += k;
            }
        }
        int ans = 0;
        for (int num : nums) {
            ans ^= num;
        }
        return ans;
    }
}