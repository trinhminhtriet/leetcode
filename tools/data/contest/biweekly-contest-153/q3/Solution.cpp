#include <vector>
#include <climits>
#include <algorithm>

using namespace std;

class Solution {
public:
    long long minimumCost(vector<int>& nums, vector<int>& cost, int k) {
        int n = nums.size();
        vector<long long> prefixNums(n + 1, 0);
        vector<long long> prefixCost(n + 1, 0);

        for (int i = 0; i < n; ++i) {
            prefixNums[i + 1] = prefixNums[i] + nums[i];
            prefixCost[i + 1] = prefixCost[i] + cost[i];
        }

        vector<vector<long long>> dp(n + 1, vector<long long>(n + 1, LLONG_MAX / 2));
        dp[0][0] = 0;

        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= i; ++j) {
                for (int m = j - 1; m < i; ++m) {
                    long long sumNums = prefixNums[i] - prefixNums[m];
                    long long sumCost = prefixCost[i] - prefixCost[m];
                    long long currentCost = (prefixNums[m] + sumNums + k * j) * sumCost;
                    if (dp[m][j - 1] + currentCost < dp[i][j]) {
                        dp[i][j] = dp[m][j - 1] + currentCost;
                    }
                }
            }
        }

        long long minCost = LLONG_MAX;
        for (int j = 1; j <= n; ++j) {
            if (dp[n][j] < minCost) {
                minCost = dp[n][j];
            }
        }
        return minCost;
    }
};
