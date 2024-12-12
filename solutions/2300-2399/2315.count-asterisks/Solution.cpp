class Solution {
public:
    int countAsterisks(string s) {
        int ans = 0, ok = 1;
        for (char& c : s) {
            if (c == '*') {
                ans += ok;
            } else if (c == '|') {
                ok ^= 1;
            }
        }
        return ans;
    }
};