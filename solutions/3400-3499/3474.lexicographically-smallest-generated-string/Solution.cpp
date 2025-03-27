#include <bits/stdc++.h>
using namespace std;

const int MOD = 1e9 + 7;
int power[501]{1};

auto init = []()
{
  for (int i = 1; i <= 500; i++)
  {
    power[i] = (31LL * power[i - 1]) % MOD;
  }
  return 0;
}();

int rolling_hash(const string_view &str)
{
  long long h = 0;
  for (size_t i = 0; i < str.size(); i++)
  {
    if (str[i] == '0')
      continue;
    h = (h + 1LL * power[i] * (str[i] - 'a' + 1)) % MOD;
  }
  return h;
}
int with(int hash, int pos, char c)
{
  return (hash + 1LL * power[pos] * (c - 'a' + 1)) % MOD;
}
class Solution
{
public:
  string generateString(const string &str1, const string &str2) const
  {
    int n = str1.size(), m = str2.size(), sz = n + m - 1;
    string res(sz, '0');
    for (int i = 0; i < n; i++)
    {
      if (str1[i] == 'T')
      {
        copy(str2.begin(), str2.end(), res.begin() + i);
      }
    }
    for (int i = 0; i < n; i++)
    {
      if ((str1[i] == 'T' && res.substr(i, m) != str2) ||
          (str1[i] == 'F' && res.substr(i, m) == str2))
      {
        return "";
      }
    }

    deque<pair<int, int>> false_list;
    int str2_hash = rolling_hash(str2);
    for (int idx = 0; idx < sz; idx++)
    {
      if (idx < n && str1[idx] == 'F')
      {
        false_list.push_back({idx, rolling_hash(string_view(res.c_str() + idx, m))});
      }
      while (!false_list.empty() && abs(false_list.front().first - idx) >= m)
      {
        false_list.pop_front();
      }

      if (res[idx] == '0')
      {
        for (char ch = 'a'; ch <= 'z'; ch++)
        {
          for (auto &[false_index, h] : false_list)
          {
            if (with(h, idx - false_index, ch) == str2_hash)
            {
              goto cannot_use;
            }
          }
          for (auto &[false_index, h] : false_list)
          {
            h = with(h, idx - false_index, ch);
          }
          res[idx] = ch;
          break;

        cannot_use:;
        }
      }
    }
    return res;
  }
};