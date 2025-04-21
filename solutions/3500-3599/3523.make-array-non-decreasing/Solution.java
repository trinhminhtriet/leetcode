import java.util.*;

class Solution {
    public int maximumPossibleSize(int[] nums) {
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = nums.length - 1; i >= 0; i--) {
            int current = nums[i];
            // Merge until current can be added safely
            while (!stack.isEmpty() && current > stack.peek()) {
                current = Math.max(current, stack.pop());
            }
            stack.push(current);
        }

        return stack.size(); // Final size of non-decreasing array
    }
}
