class Solution {
    public long perfectPairs(int[] nums) {
        int n = nums.length;
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Math.abs((long) nums[i]);
        }
        java.util.Arrays.sort(arr);

        long count = 0;
        int j = 0;
        for (int i = 0; i < n; i++) {
            while (j < n && arr[j] <= 2 * arr[i]) {
                j++;
            }
            count += (j - i - 1);
        }
        return count;
    }
}