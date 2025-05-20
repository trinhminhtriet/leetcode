class Solution {
    public static int minOperations(int[] nums) {
        int n = nums.length;
        int ans = 0;
        boolean[] isNumEncountered = new boolean[100001];
        int[] monoStack = new int[n];
        int size = 0;

        for (int i = 0; i < n; i++) {
            int curr = nums[i];

            if (curr == 0) {
                while (size > 0) {
                    isNumEncountered[monoStack[--size]] = false;
                }
                continue;
            }

            while (size > 0 && monoStack[size - 1] > curr) {
                isNumEncountered[monoStack[--size]] = false;
            }

            if (!isNumEncountered[curr]) {
                ans++;
                isNumEncountered[curr] = true;
            }

            monoStack[size++] = curr;
        }

        return ans;
    }
}
