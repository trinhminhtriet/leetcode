class Solution
{
public:
  int shortestMatchingSubstring(string s, string p)
  {
    int first_star = p.find('*');
    int second_star = p.find('*', first_star + 1);

    string prefix = p.substr(0, first_star);
    string middle = p.substr(first_star + 1, second_star - first_star - 1);
    string suffix = p.substr(second_star + 1);

    if (prefix.empty() && middle.empty() && suffix.empty())
    {
      return 0;
    }

    int len_pre = prefix.length();
    int len_mid = middle.length();
    int len_suf = suffix.length();

    vector<int> list_p, list_m, list_s;
    long long mini = LLONG_MAX;

    auto findocc = [&](string &part, vector<int> &positions)
    {
      int pos = s.find(part, 0);
      while (pos != string::npos)
      {
        positions.push_back(pos);
        pos = s.find(part, pos + 1);
      }
    };

    if (!prefix.empty())
      findocc(prefix, list_p);
    if (!middle.empty())
      findocc(middle, list_m);
    if (!suffix.empty())
      findocc(suffix, list_s);

    if (!prefix.empty() && !middle.empty() && !suffix.empty())
    {

      for (auto i : list_p)
      {
        auto it_m = lower_bound(list_m.begin(), list_m.end(), i + len_pre);
        if (it_m == list_m.end())
          continue;
        int m_start = *it_m;
        auto it_s = lower_bound(list_s.begin(), list_s.end(), m_start + len_mid);
        if (it_s == list_s.end())
          continue;
        int s_start = *it_s;
        mini = min(mini, (long long)(s_start + len_suf - i));
      }
    }
    else if (prefix.empty() && !middle.empty() && !suffix.empty())
    {
      for (auto m_start : list_m)
      {
        auto it_s = lower_bound(list_s.begin(), list_s.end(), m_start + len_mid);
        if (it_s == list_s.end())
          continue;
        int s_start = *it_s;
        mini = min(mini, (long long)(s_start + len_suf - m_start));
      }
    }
    else if (!prefix.empty() && middle.empty() && !suffix.empty())
    {
      for (auto i : list_p)
      {
        auto it_s = lower_bound(list_s.begin(), list_s.end(), i + len_pre);
        if (it_s == list_s.end())
          continue;
        int s_start = *it_s;
        mini = min(mini, (long long)(s_start + len_suf - i));
      }
    }
    else if (!prefix.empty() && !middle.empty() && suffix.empty())
    {
      for (auto i : list_p)
      {
        auto it_m = lower_bound(list_m.begin(), list_m.end(), i + len_pre);
        if (it_m == list_m.end())
          continue;
        int m_start = *it_m;
        mini = min(mini, (long long)((m_start + len_mid) - i));
      }
    }
    else if (!prefix.empty() && middle.empty() && suffix.empty())
    {
      if (!list_p.empty())
        mini = len_pre;
    }
    else if (prefix.empty() && !middle.empty() && suffix.empty())
    {
      if (!list_m.empty())
        mini = len_mid;
    }
    else if (prefix.empty() && middle.empty() && !suffix.empty())
    {
      if (!list_s.empty())
        mini = len_suf;
    }

    return (mini == LLONG_MAX) ? -1 : (int)mini;
  }
};