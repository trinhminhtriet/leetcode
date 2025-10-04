class Solution {
    public int maxXorSubsequences(int[] nums) {
        int[] b = new int[31];
        for (int num : nums) {
            for (int i = 30, x = num; i >= 0; i--) {
                if ((x & (1 << i)) != 0) {
                    if (b[i] == 0) {
                        b[i] = x;
                        break;
                    }
                    x ^= b[i];
                }
            }
        }
        
        int max = 0;
        for (int i = 30; i >= 0; i--) {
            max = Math.max(max, max ^ b[i]);
        }
        return max;
    }
}