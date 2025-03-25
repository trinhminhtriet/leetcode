class Solution {
    public:
        int dfs(int i, int m, vector<vector<pair<int, int>>> &ral, vector<int> &vis) {
            int res = vis[i] = 1;
            for (auto [j, w] : ral[i])
                if (w <= m && !vis[j])
                    res += dfs(j, m, ral, vis);
            return res;
        }
        int minMaxWeight(int n, vector<vector<int>>& edges, int threshold) {
            vector<vector<pair<int, int>>> ral(n);
            for (auto &e : edges)
                ral[e[1]].push_back({e[0], e[2]});
            int l = 1, r = 1000001;
            while (l < r) {
                int m = (l + r) / 2;
                if (vector<int> vis(n); dfs(0, m, ral, vis) == n)
                    r = m;
                else
                    l = m + 1;
            }
            return l == 1000001 ? -1 : l;
        }
};