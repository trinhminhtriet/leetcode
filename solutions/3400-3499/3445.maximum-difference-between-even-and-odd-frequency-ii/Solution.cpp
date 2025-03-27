class Solution
{
public:
  int maxDifference(string s, int k)
  {
    int n = s.size();
    vector pre(5, vector(n, 0)), closest_right(5, vector(n, n));
    for (int x = 0; x < 5; x++)
    {
      for (int i = 0; i < n; i++)
      {
        int num = s[i] - '0';
        pre[x][i] = (num == x);
        if (i > 0)
          pre[x][i] += pre[x][i - 1];
      }
      for (int i = n - 1; i >= 0; i--)
      {
        int num = s[i] - '0';
        if (i < n - 1)
          closest_right[x][i] = closest_right[x][i + 1];
        if (num == x)
          closest_right[x][i] = i;
      }
    }
    int ans = INT_MIN;
    auto go = [&](int odd, int even) -> int
    {
      vector suf(2, vector(2, vector(n, INT_MIN)));

      for (int end_idx = 0; end_idx < n; end_idx++)
      {
        int odd_parity = pre[odd][end_idx] % 2;
        int even_parity = pre[even][end_idx] % 2;
        if (pre[odd][end_idx] > 0 && pre[even][end_idx] > 0)
        {
          suf[odd_parity][even_parity][end_idx] = pre[odd][end_idx] - pre[even][end_idx];
        }
      }
      for (int odd_parity = 0; odd_parity < 2; odd_parity++)
      {
        for (int even_parity = 0; even_parity < 2; even_parity++)
        {
          for (int end_idx = n - 2; end_idx >= 0; end_idx--)
          {
            suf[odd_parity][even_parity][end_idx] = max(
                suf[odd_parity][even_parity][end_idx],
                suf[odd_parity][even_parity][end_idx + 1]);
          }
        }
      }
      for (int start_idx = 0; start_idx < n; start_idx++)
      {
        int min_end_idx = start_idx + k - 1;
        if (min_end_idx >= n)
          break;

        int odd_below_i = (start_idx == 0 ? 0 : pre[odd][start_idx - 1]);
        int even_below_i = (start_idx == 0 ? 0 : pre[even][start_idx - 1]);

        int good_odd_parity = (odd_below_i + 1) % 2;
        int good_even_parity = (even_below_i) % 2;

        int query = max({start_idx + k - 1, closest_right[odd][start_idx], closest_right[even][start_idx]});
        if (query >= n)
          continue;

        int val = suf[good_odd_parity][good_even_parity][query];
        if (val == INT_MIN)
          continue;

        ans = max(ans, val - odd_below_i + even_below_i);
      }
      return ans;
    };
    for (int a = 0; a <= 4; a++)
    {
      for (int b = 0; b <= 4; b++)
      {
        if (a != b)
        {
          go(a, b);
        }
      }
    }
    return ans;
  }
};