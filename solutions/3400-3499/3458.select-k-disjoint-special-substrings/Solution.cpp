class Solution
{
public:
  bool maxSubstringLength(string s, int k)
  {
    int n = s.size();
    vector<int> first(26, -1), last(26, -1);
    for (int i = 0; i < n; i++)
    {
      int c = s[i] - 'a';
      if (first[c] == -1)
        first[c] = i;
      last[c] = i;
    }

    vector<pair<int, int>> intervals;
    for (int i = 0; i < n; i++)
    {
      int c = s[i] - 'a';
      if (i != first[c])
        continue;

      int j = last[c];
      for (int k = i; k <= j; k++)
      {
        j = max(j, last[s[k] - 'a']);
      }

      bool flag = true;
      for (int k = i; k <= j; k++)
      {
        if (first[s[k] - 'a'] < i)
        {
          flag = false;
          break;
        }
      }
      if (!flag)
        continue;

      if (i == 0 && j == n - 1)
        continue;

      intervals.push_back({i, j});
    }

    sort(intervals.begin(), intervals.end(), [](auto &a, auto &b)
         { return a.second < b.second; });

    int cnt = 0;
    int currend = -1;
    for (auto &i : intervals)
    {
      if (i.first > currend)
      {
        cnt++;
        currend = i.second;
      }
    }

    return cnt >= k;
  }
};