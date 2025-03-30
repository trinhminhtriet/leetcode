class Solution
{
public:
    int min_r = INT_MAX, max_r = INT_MIN;
    void dfs(int l, int r, int n, int round)
    {
        if (l == r)
        {
            min_r = min(min_r, round);
            max_r = max(max_r, round);
        }
        else if (l > r)
            swap(l, r);
        for (int i = 1; i < l + 1; ++i)
            for (int j = l - i + 1; i + j <= min(r, (n + 1) / 2); ++j)
                if (l + r - (i + j) <= n / 2)
                    dfs(i, j, (n + 1) / 2, round + 1);
    }
    vector<int> earliestAndLatest(int n, int first, int second)
    {
        dfs(first, n - second + 1, n, 1);
        return {min_r, max_r};
    }
};
