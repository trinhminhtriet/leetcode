class Solution {
    public long[] resultArray(int[] A, int k) {
        long[] res = new long[k];
        int[] cnt = new int[k];
        for (int a : A) {
            int[] cnt2 = new int[k];
            for (int i = 0; i < k; i++) {
                int v = (int) (((long) i * a) % k);
                cnt2[v] += cnt[i];
                res[v] += cnt[i];
            }
            cnt = cnt2;
            cnt[a % k]++;
            res[a % k]++;
        }
        return res;
    }
}
