class Solution {
    private static final int mod = 1000000007;
    private int mod_add(int a, int b) {
        a %= mod; b %= mod;
        return ((a + b) % mod + mod) % mod;
    }
    public int lengthAfterTransformations(String s, int t) {
        int[] nums = new int[26];
        for (char ch : s.toCharArray()) nums[ch - 'a']++;
        while (t-- > 0) {
            int[] cur = new int[26];
            for (int j = 0; j < 26; j++) {
                if (j == 25 && nums[j] > 0) {
                    cur[0] = mod_add(cur[0], nums[j]);
                    cur[1] = mod_add(cur[1], nums[j]);
                } else {
                    if (j < 25) cur[j + 1] = mod_add(cur[j + 1], nums[j]);
                }
            }
            nums = cur;
        }
        int ans = 0;
        for (int i : nums) ans = mod_add(ans, i);
        return ans;
    }
}
