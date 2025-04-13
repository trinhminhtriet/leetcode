import java.util.Arrays;

class Solution {
    public int minOperations(int[] nums, int k) {
        int sum = Arrays.stream(nums).sum();
        return sum % k;
    }
}
