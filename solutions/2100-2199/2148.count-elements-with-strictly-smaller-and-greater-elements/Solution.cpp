class Solution {
public:
    // Function to count the elements that are greater than the minimum
    // and less than the maximum elements in the given vector nums
    int countElements(vector<int>& nums) {
        // Initialize minimum and maximum values with extremes
        int minVal = INT_MAX; // Use INT_MAX to represent initially the largest possible integer
        int maxVal = INT_MIN; // Use INT_MIN to represent initially the smallest possible integer
      
        // Loop to find the smallest and largest values in the vector
        for (int num : nums) {
            minVal = std::min(minVal, num); // Update minVal to the minimum found so far
            maxVal = std::max(maxVal, num); // Update maxVal to the maximum found so far
        }
      
        // Initialize the answer count
        int ans = 0;
      
        // Iterate through the elements of nums
        for (int num : nums) {
            // Increment ans if num is greater than minVal and less than maxVal
            if (minVal < num && num < maxVal)
                ans++;
        }
      
        // Return the count of elements that satisfy the condition
        return ans;
    }
};
