class Solution {
    public int alternatingSum(int[] nums) {
        int s = 1;
        int sum = 0;
        for (int x : nums) {
            sum += x * s;
            s *= -1;
        }
        return sum;
    }
}
