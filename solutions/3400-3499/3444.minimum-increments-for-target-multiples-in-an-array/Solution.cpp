class Solution
{
  long long lcm(long long a, long long b)
  {
    return a / __gcd(a, b) * b;
  }

public:
  int minimumIncrements(vector<int> &nums, vector<int> &target)
  {
    long long k = target.size();
    unordered_map<long long, long long> mpp;

    for (long long mask = 1; mask < (1 << k); mask++)
    {
      vector<long long> subset;
      for (long long i = 0; i < k; i++)
      {
        if (mask & (1 << i))
        {
          subset.push_back(target[i]);
        }
      }
      long long currlcm = subset[0];
      for (long long j = 1; j < subset.size(); j++)
      {
        currlcm = lcm(currlcm, subset[j]);
      }
      mpp[mask] = currlcm;
    }

    long long fullmask = (1 << k) - 1;
    vector<long long> dp(1 << k, INT_MAX);
    dp[0] = 0;

    for (auto num : nums)
    {
      vector<pair<long long, long long>> maskcost;
      for (auto &entry : mpp)
      {
        long long mask = entry.first;
        long long lcmval = entry.second;
        long long rm = num % lcmval;
        long long cost = (rm == 0) ? 0 : (lcmval - rm);
        maskcost.emplace_back(mask, cost);
      }

      vector<long long> newdp = dp;
      for (long long prevmask = 0; prevmask < (1 << k); prevmask++)
      {
        if (dp[prevmask] == INT_MAX)
          continue;
        for (auto &mc : maskcost)
        {
          long long mask = mc.first;
          long long cost = mc.second;
          long long nmask = prevmask | mask;
          long long ncost = dp[prevmask] + cost;
          if (ncost < newdp[nmask])
          {
            newdp[nmask] = ncost;
          }
        }
      }

      dp = newdp;
    }

    return dp[fullmask] == INT_MAX ? -1 : dp[fullmask];
  }
};