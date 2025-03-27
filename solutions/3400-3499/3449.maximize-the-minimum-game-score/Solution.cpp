class Solution
{
public:
  long long maxScore(vector<int> &points, int m)
  {
    int n = points.size();
    if (m < n)
      return 0;

    auto can = [&](long long val) -> bool
    {
      long long total = 0, transfer = 0, skipAdd = 0;
      for (int i = 0; i < n && total <= m; i++)
      {
        int point = points[i];
        long long necessary = (val + point - 1) / point;
        if (transfer >= necessary)
        {
          transfer = 0;
          skipAdd++;
        }
        else
        {
          long long p = transfer * point;
          long long ops = (((val - p) + point - 1) / point);
          total += 2 * ops - 1;
          total += skipAdd;

          transfer = max(ops - 1, 0LL);
          skipAdd = 0;
        }
      }
      return total <= m;
    };

    long long l = 1, r = 1e18, ans = 0;
    while (l <= r)
    {
      long long m = l + (r - l) / 2;
      if (can(m))
      {
        ans = m;
        l = m + 1;
      }
      else
      {
        r = m - 1;
      }
    }
    return ans;
  }
};