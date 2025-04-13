import java.util.Arrays;

class Solution {
    public int minOperations(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        int mod = sum % k;
        if (mod == 0)
            return 0;

        Arrays.sort(nums);
        int operations = 0;
        int reduction = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            int maxReduce = Math.min(nums[i], mod - reduction);
            operations += maxReduce;
            reduction += maxReduce;
            if (reduction >= mod)
                break;
        }

        return operations;
    }
}
