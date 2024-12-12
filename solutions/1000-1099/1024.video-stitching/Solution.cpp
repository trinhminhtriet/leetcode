class Solution {
public:
    int videoStitching(vector<vector<int>>& clips, int time) {
        vector<int> last(time);
        for (auto& v : clips) {
            int a = v[0], b = v[1];
            if (a < time) {
                last[a] = max(last[a], b);
            }
        }
        int mx = 0, ans = 0;
        int pre = 0;
        for (int i = 0; i < time; ++i) {
            mx = max(mx, last[i]);
            if (mx <= i) {
                return -1;
            }
            if (pre == i) {
                ++ans;
                pre = mx;
            }
        }
        return ans;
    }
};