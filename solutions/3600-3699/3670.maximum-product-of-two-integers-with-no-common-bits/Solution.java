class Solution {
    public long maxProduct(int[] nums) {
        int max = 0;
        for (int num : nums) {
            max = Math.max(max, num);
        }
        int mask = (1 << (32 - Integer.numberOfLeadingZeros(max))) - 1;
        boolean[] has = new boolean[mask + 1];
        for (int num : nums) {
            has[num] = true;
        }
        long res = 0;
        for (int i = mask / 2; i >= 1; i--) {
            if (!has[i])
                continue;
            for (int j = i ^ mask; (long) j * i > res; j = (j - 1) & (i ^ mask)) {
                if (has[j]) {
                    res = (long) j * i;
                    break;
                }
            }
        }
        return res;
    }
}