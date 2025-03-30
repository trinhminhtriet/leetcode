class Solution
{
public:
    int waysToBuildRooms(vector<int> &prevRoom)
    {
        int n = prevRoom.size();

        // Construct tree.
        vector<vector<int>> g(n);
        for (int i = 1; i < n; ++i)
        {
            g[prevRoom[i]].push_back(i);
        }

        // Pre-process fac and inv fac.
        vector<ll> fac(n + 1, 1), ifac(n + 1, 1);
        for (int i = 2; i <= n; ++i)
        {
            fac[i] = fac[i - 1] * i % mod;
            ifac[i] = qpow(fac[i], mod - 2);
        }

        return dfs(g, fac, ifac, 0).first;
    }

private:
    using ll = long long;
    static constexpr int mod = 1e9 + 7;

    ll qpow(ll x, size_t n)
    {
        ll ans = 1;
        for (auto i = n; i; i /= 2)
        {
            if (i % 2)
                ans = ans * x % mod;
            x = x * x % mod;
        }
        return ans;
    }

    pair<ll, ll> dfs(const vector<vector<int>> &g, const vector<ll> &fac, const vector<ll> &ifac, int cur)
    {
        if (g[cur].size() == 0)
            return {1, 1};
        ll ans = 1, l = 0;
        for (int nxt : g[cur])
        {
            auto [tmp, r] = dfs(g, fac, ifac, nxt);
            ll comb = (((fac[l + r] * ifac[l]) % mod) * ifac[r]) % mod;
            ans = (ans * tmp % mod) * comb % mod;
            l += r;
        }
        return {ans, l + 1};
    }
};
