class Solution {
public:
    int findGoodStrings(int n, string s1, string s2, string evil) {
      // Initialize the dp array
        dp.resize(n, vector<vector<vector<int>>>(evil.length(), vector<vector<int>>(2, vector<int>(2, -1))));
        nextMatchedCount.resize(evil.length(), vector<int>(26, -1));
        
        return find(s1, s2, evil, 0, 0, true, true, getLPS(evil));
    }
private:
    static constexpr int kMod = 1'000'000'007;
    vector<vector<vector<vector<int>>>> dp;
    vector<vector<int>> nextMatchedCount;
    
    int find(const string& s1, const string& s2, const string& evil, int i, int matchedEvilCount, bool isS1Prefix, bool isS2Prefix, const vector<int>& evilLPS) {
        if (matchedEvilCount == evil.length()) return 0;
        if (i == s1.length()) return 1;

        int& ans = dp[i][matchedEvilCount][isS1Prefix][isS2Prefix];
        if (ans != -1) return ans;

        ans = 0;
        const char minChar = isS1Prefix ? s1[i] : 'a';
        const char maxChar = isS2Prefix ? s2[i] : 'z';
        
        for (char c = minChar; c <= maxChar; ++c) {
            const int nextMatchedEvilCount = getNextMatchedEvilCount(evil, matchedEvilCount, c, evilLPS);
            ans += find(s1, s2, evil, i + 1, nextMatchedEvilCount, isS1Prefix && c == s1[i], isS2Prefix && c == s2[i], evilLPS);
            ans %= kMod;
        }
        return ans;
    }

    // Get Longest Prefix also Suffix
    vector<int> getLPS(const string& s) {
        vector<int> lps(s.length());
        for (int i = 1, j = 0; i < s.length(); ++i) {
            while (j > 0 && s[j] != s[i]) j = lps[j - 1];
            if (s[i] == s[j]) lps[i] = ++j;
        }
        return lps;
    }

    int getNextMatchedEvilCount(const string& evil, int j, char currChar, const vector<int>& evilLPS) {
        int& ans = nextMatchedCount[j][currChar - 'a'];

        if (ans != -1) return ans;
        while (j > 0 && evil[j] != currChar) j = evilLPS[j - 1];
        return ans = (evil[j] == currChar ? j + 1 : j);
    }
};