class Solution {
    public long bowlSubarrays(int[] nums) {
        Stack<Integer> s = new Stack<>();
        long ans = 0;
        for(int i = 0; i < nums.length ; i++) {
            while(!s.isEmpty() && s.peek() < nums[i]) {
                s.pop();
                if(!s.isEmpty()) {
                    ans++;
                }
            }
            s.push(nums[i]);
        }
        return ans;
    }
}