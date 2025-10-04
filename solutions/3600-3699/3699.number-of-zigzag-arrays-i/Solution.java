class Solution {
    private final int MOD = 1_000_000_007;

    public int zigZagArrays(int n, int l, int r) {
        if (n == 1) return (r - l + 1) % MOD;
        
        int[] prefix = new int[r + 1];

        for (int i = l; i <= r; i++) {
            prefix[i] = (prefix[i - 1] + 1) % MOD;
        }

        for (int idx = n - 2; idx >= 0; idx--) {
            int[] nextDp = new int[r + 1];
            int[] nextPrefix = new int[r + 1];
            
            boolean lookForGreater = (idx % 2 == 0);

            for (int num = l; num <= r; num++) {
                if (lookForGreater) {
                    nextDp[num] = (prefix[r] - prefix[num] + MOD) % MOD;
                } else {
                    nextDp[num] = (prefix[num - 1] - prefix[l - 1] + MOD) % MOD;
                }
            }

            for (int i = l; i <= r; i++) {
                nextPrefix[i] = (nextPrefix[i - 1] + nextDp[i]) % MOD;
            }
            
            prefix = nextPrefix;
        }
        
        return (int) ((long) prefix[r] * 2 % MOD);
    }
}