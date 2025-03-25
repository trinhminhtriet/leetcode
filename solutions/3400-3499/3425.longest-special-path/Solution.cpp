class Solution {
public:
    pair<int, int> res{0, 1};
    int depth[50001] = {};
    void dfs(int i, int p, vector<vector<array<int, 2>>>& al, vector<int>& nums, int left, int cur_depth, vector<int> &ps) {
        int prev_depth = exchange(depth[nums[i]], cur_depth);
        left = max(left, prev_depth);
        res = min(res, {-(ps.back() - ps[left]), cur_depth - left});
        for (auto &[j, l] : al[i])
            if (j != p) {
                ps.push_back(ps.back() + l);
                dfs(j, i, al, nums, left, cur_depth + 1, ps);
                ps.pop_back();
            }
        depth[nums[i]] = prev_depth;
    }
    vector<int> longestSpecialPath(vector<vector<int>>& edges, vector<int>& nums) {
        vector<vector<array<int, 2>>> al(nums.size());
        for (const auto &e : edges) {
            al[e[0]].push_back({e[1], e[2]});
            al[e[1]].push_back({e[0], e[2]});
        }
        dfs(0, -1, al, nums, 0, 1, vector<int>() = {0});
        return {-res.first, res.second};
    }
};