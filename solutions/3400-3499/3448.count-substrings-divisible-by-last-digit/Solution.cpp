class Solution
{
public:
  long long countSubstrings(string s)
  {
    long long ans = 0;
    int n = s.size();
    vector<vector<vector<int>>> dp(
        n + 1, vector<vector<int>>(10, vector<int>(10)));
    for (int i = 1; i <= n; ++i)
    {
      int digit = s[i - 1] - '0';
      for (int num = 1; num <= 9; ++num)
      {
        for (int rem = 0; rem < num; ++rem)
        {
          dp[i][num][((rem * 10) + digit) % num] +=
              dp[i - 1][num][rem];
        }
        dp[i][num][digit % num]++;
      }
      ans += dp[i][digit][0];
    }
    return ans;
  }
};