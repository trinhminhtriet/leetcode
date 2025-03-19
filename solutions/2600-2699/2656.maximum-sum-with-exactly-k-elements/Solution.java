class Solution {
    public int maximizeSum(int[] nums, int k) {
        // Create a max heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        // Add all elements to the heap
        for (int num : nums) {
            maxHeap.offer(num);
        }
        
        int score = 0;
        
        // Perform k operations
        for (int i = 0; i < k; i++) {
            // Get the maximum element
            int max = maxHeap.poll();
            // Add it to score
            score += max;
            // Add back the element increased by 1
            maxHeap.offer(max + 1);
        }
        
        return score;
    }
}