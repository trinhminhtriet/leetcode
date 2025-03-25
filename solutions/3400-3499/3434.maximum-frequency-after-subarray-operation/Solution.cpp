class Solution {
    public:
        int maxFrequency(vector<int>& nums, int k) {
            int orig = count(nums.begin(), nums.end(), k), mx = 0;
            for (int m = 1; m <= 50; ++m) {
                if (m == k) continue;
                int cur = 0, mxCur = 0;
                for (int n : nums) {
                    cur += n == m ? 1 : n == k ? -1 : 0;
                    cur = max(cur, 0);
                    mxCur = max(mxCur, cur);
                }
                mx = max(mx, mxCur);
            }
            return orig + mx;
        }
    };