struct Fenwick
{
    int n;
    vector<long long> BIT;
    Fenwick(int n) : n(n), BIT(n + 1, 0) {}
    void update(int i, long long delta)
    {
        for (; i <= n; i += i & -i)
            BIT[i] += delta;
    }
    long long query(int i)
    {
        long long sum = 0;
        for (; i; i -= i & -i)
            sum += BIT[i];
        return sum;
    }
};

struct Tree
{
    int N, root = 1;
    vector<vector<pair<int, int>>> adj;
    vector<int> tin, tout, par, depth;
    vector<long long> rootD;
    int timer = 0;
    Tree(int _N)
    {
        N = _N;
        adj.resize(N + 1);
        tin.resize(N + 1);
        tout.resize(N + 1);
        par.resize(N + 1);
        depth.resize(N + 1);
        rootD.resize(N + 1);
    }
    void add_edge(int u, int v, int w)
    {
        adj[u].push_back({v, w});
        adj[v].push_back({u, w});
    }
    void init(int rt = 1)
    {
        root = rt;
        timer = 0;
        function<void(int, int, long long)> dfs = [&](int u, int p, long long dist)
        {
            par[u] = p;
            rootD[u] = dist;
            tin[u] = ++timer;
            depth[u] = depth[p] + 1;
            for (auto &[v, w] : adj[u])
                if (v != p)
                    dfs(v, u, dist + w);
            tout[u] = timer;
        };
        dfs(root, 0, 0);
        // DFS Preprocessing for calcualting parent , rootD , and tin and tout times
    }
};

class Solution
{
public:
    vector<int> treeQueries(int n, vector<vector<int>> &edges, vector<vector<int>> &queries)
    {
        Tree tree(n);
        vector<int> ans;
        for (auto &e : edges)
            tree.add_edge(e[0], e[1], e[2]);
        tree.init(1);
        Fenwick F(tree.timer);
        vector<int> up(n + 1, 0);
        for (int i = 2; i <= n; ++i)
            for (auto &[u, w] : tree.adj[i])
                if (u == tree.par[i])
                    up[i] = w;
        for (auto &q : queries)
        {
            if (q[0] == 1)
            {
                int u = q[1], v = q[2], c = v;
                if (tree.par[u] == v)
                    c = u; // c = child
                // Range Update in Fenwick Tree , the change in weight
                // delta = q[3] - up[c]
                // we add this value at tin[child]
                // and subtract at tout[child] + 1
                F.update(tree.tin[c], q[3] - up[c]);
                F.update(tree.tout[c] + 1, up[c] - q[3]);
                up[c] = q[3];
            }
            else
                ans.push_back((int)(tree.rootD[q[1]] + F.query(tree.tin[q[1]])));
            //                             intial weight   + changes done in weights
        }
        return ans;
    }
};
