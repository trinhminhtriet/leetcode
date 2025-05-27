class Solution {
    public long minCuttingCost(int n, int m, int k) {

        long ans = 0;

        if (m <= k && n <= k)
            return 0;

        if (m > k && n <= k) {
            ans += (long) (m - k) * k;
        }
        if (n > k && m <= k) {
            ans += (long) (n - k) * k;
        }

        return ans;
    }
}
