class Solution {
  public:
      int maxFreeTime(int t, int k, vector<int>& st, vector<int>& et) {
          vector<pair<int, int>> w;
          int n = st.size();
          w.reserve(n + 2);
          w.emplace_back(0, 0);
          for(int i = 0; i < n; i++) {
              w.emplace_back(st[i], et[i]);
          }
          w.emplace_back(t, t);
          k++;
          int ans = 0;
          for(int i = 0; i < k; i++) {
              ans += w[i + 1].first - w[i].second;
          }
          for(int i = k, l = 0, temp = ans; i < n + 1; i++, l++) {
              temp -= w[l + 1].first - w[l].second;
              temp += w[i + 1].first - w[i].second;
              ans = max(ans, temp);
          }
          return ans;
      }
  }
;