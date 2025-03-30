

#define ll long long

class SGT
{
public:
    vector<ll> seg;

    SGT(ll n)
    {
        seg.resize(4 * n + 1);
    }

    void build(ll ind, ll low, ll high, vector<ll> &arr)
    {
        if (low == high)
        {
            seg[ind] = arr[low];
            return;
        }

        ll mid = (low + high) / 2;

        build(2 * ind + 1, low, mid, arr);
        build(2 * ind + 2, mid + 1, high, arr);

        seg[ind] = max(seg[2 * ind + 1], seg[2 * ind + 2]);
    }

    ll query(ll ind, ll low, ll high, ll l, ll r)
    {
        if (r < low || high < l)
            return 0;

        if (low >= l && high <= r)
            return seg[ind];

        ll mid = (low + high) >> 1;
        ll left = query(2 * ind + 1, low, mid, l, r);
        ll right = query(2 * ind + 2, mid + 1, high, l, r);

        return max(left, right);
    }

    void update(ll ind, ll low, ll high, ll i, ll val)
    {
        if (low == high)
        {
            seg[ind] = val;
            return;
        }

        ll mid = (low + high) >> 1;

        if (i <= mid)
            update(2 * ind + 1, low, mid, i, val);
        else
            update(2 * ind + 2, mid + 1, high, i, val);

        seg[ind] = max(seg[2 * ind + 1], seg[2 * ind + 2]);
    }
};

ll res;

void find(ll val, SGT &st, ll n)
{
    ll low = 0;
    ll high = n - 1;

    ll ans = -1;

    while (low <= high)
    {
        ll mid = (low + high) / 2;
        ll mx = st.query(0, 0, n - 1, 0, mid); // finding max value in the range 0 to mid.

        if (mx >= val) // implies that there is an idex that satisfies the condition in the range 0 to mid.
        {
            ans = mid;
            high = mid - 1; // looking for index further left.
        }

        else
            low = mid + 1;
    }

    if (ans != -1)
        st.update(0, 0, n - 1, ans, 0);

    else // if no suitable index is found increase the answer.
        ++res;

    return;
}

class Solution
{
public:
    int numOfUnplacedFruits(vector<int> &f, vector<int> &b)
    {

        res = 0; // global variable res initialised to zero;

        int n = f.size();
        vector<ll> tb(b.begin(), b.end());

        SGT st(n);
        st.build(0, 0, n - 1, tb); // building the segment tree.

        for (int i = 0; i < n; ++i)
            find(f[i], st, n); // checking answer for each fruit.

        return res;
    }
};
