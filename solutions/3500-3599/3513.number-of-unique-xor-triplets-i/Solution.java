class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int n = nums.length;
        if (n == 1 || n == 2) {
            return n;
        }
        int near = (int) (Math.log(n) / Math.log(2));
        return 1 << (near + 1);
    }
}
