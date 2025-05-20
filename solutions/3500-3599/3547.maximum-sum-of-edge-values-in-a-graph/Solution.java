class Solution {
    public long maxScore(int n, int[][] edges) {
        long res = ((2L * n + 3) * n - 11) * n + 6;
        res /= 6;
        if (n == edges.length) {
            res += 2;
        }
        return res;
    }
}
