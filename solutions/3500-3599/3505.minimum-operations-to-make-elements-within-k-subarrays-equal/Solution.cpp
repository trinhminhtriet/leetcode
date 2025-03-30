class Solution
{
public:
    int n;

    long long dp[16][100001];
    long long mvs[100001];
    int k;
    int x;
    long long solve(int par, int i)
    {
        if (par == k)
        {
            return 0;
        }
        if (i == n)
        {
            return 1e15;
        }
        if (dp[par][i] != -1)
            return dp[par][i];
        long long ans = 9e18;

        if (i + x - 1 < n)
        {
            ans = min(ans, mvs[i] + solve(par + 1, i + x));
        }
        ans = min(ans, solve(par, i + 1));
        return dp[par][i] = ans;
    }

    long long minOperations(vector<int> &a, int _x, int _k)
    {
        n = (int)a.size();
        k = _k;
        x = _x;
        multiset<int> f;
        multiset<int> s;
        long long s1 = 0;
        long long s2 = 0;
        for (int i = 0; i < n; i++)
        {
            if (i >= x)
            {
                if (f.find(a[i - x]) != f.end())
                {
                    f.erase(f.find(a[i - x]));
                    s1 -= a[i - x];
                }
                else
                {
                    s.erase(s.find(a[i - x]));
                    s2 -= a[i - x];
                }
            }
            if ((int)f.size() <= (int)s.size())
            {
                f.insert(a[i]);
                s1 += a[i];
            }
            else
            {
                s.insert(a[i]);
                s2 += a[i];
            }
            int n1 = (int)f.size();
            int n2 = (int)s.size();

            while (n1 > 0 && n2 > 0)
            {
                int mn = *f.rbegin();
                int mx = *s.begin();
                if (mn > mx)
                {
                    s1 -= mn;
                    s1 += mx;
                    s2 -= mx;
                    s2 += mn;
                    f.insert(mx);
                    s.insert(mn);
                    f.erase(f.find(mn));
                    s.erase(s.find(mx));
                    n1 = (int)f.size();
                    n2 = (int)s.size();
                }
                else
                {
                    break;
                }
            }
            if (i >= x - 1)
            {
                int m = *f.rbegin();
                mvs[i - x + 1] = ((n1 * 1LL * m) - s1) + 0LL + (s2 - (m * 1LL * n2));
            }
        }
        memset(dp, -1, sizeof(dp));
        return solve(0, 0);
    }
};
