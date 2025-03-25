public class Solution {
    public int maxFrequency(int[] nums, int k) {
        int orig = 0;
        for (int num : nums) {
            if (num == k) orig++;
        }
        int maxGain = 0;
        for (int m = 1; m <= 50; m++) {
            if (m == k) continue;
            int current = 0, maxCurrent = 0;
            for (int num : nums) {
                current += (num == m) ? 1 : (num == k) ? -1 : 0;
                current = Math.max(current, 0);
                maxCurrent = Math.max(maxCurrent, current);
            }
            maxGain = Math.max(maxGain, maxCurrent);
        }
        return orig + maxGain;
    }
}