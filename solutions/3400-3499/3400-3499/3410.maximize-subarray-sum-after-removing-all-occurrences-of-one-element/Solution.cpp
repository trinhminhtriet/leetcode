class Solution {
    public:
        long long maxSubarraySum(vector<int>& nums) {
            unordered_map<long, long> pre;
    
            long res = nums[0];
            long prefix = 0;
            long low = 0;
            pre[0] = 0;
    
            for (auto n: nums) {
                prefix += n;
                res = max(res, prefix - low);
    
                if (n < 0) {
                    if (pre.count(n)) {
                        pre[n] = min(pre[n], pre[0]) + n;
                    } else {
                        pre[n] = pre[0] + n;
                    }
                    low = min(low, pre[n]);
                }
    
                pre[0] = min(pre[0], prefix);
                low = min(low, pre[0]);
            }
    
            return res;
        }
    };