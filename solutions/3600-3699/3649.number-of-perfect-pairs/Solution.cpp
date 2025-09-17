class Solution {
public:
    long long perfectPairs(vector<int>& nums) {
        int n = nums.size();

        vector<long long> arr;
        for(auto num : nums){
            long long x = num;
            arr.push_back(abs(x));
        }
        sort(arr.begin(), arr.end());

        long long count = 0;
        int j = 0;

        for(int i = 0; i < n; i++){
            while(j < n && arr[j] <= 2 * arr[i]){
                j++;
            }
            count += (j - i - 1);
        }
        return count;
    }
};