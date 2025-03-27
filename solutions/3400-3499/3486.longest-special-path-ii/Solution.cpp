class Solution
{
  // For output
  int longSpecial = 0;
  int minCount = 1;
  // For internal data
  int n;
  vector<vector<pair<int, int>>> adj; // node, length
  vector<int> trace;

public:
  vector<int> longestSpecialPath(vector<vector<int>> &edges, vector<int> &nums)
  {
    n = edges.size() + 1;
    adj.resize(n);
    trace.resize(50001, -1);
    for (auto &e : edges)
    {
      adj[e[0]].emplace_back(e[1], e[2]);
      adj[e[1]].emplace_back(e[0], e[2]);
    }
    vector<int> path;
    DFS(0, -1, 0, 0, 0, path, nums, -1);
    return {longSpecial, minCount};
  }

  void DFS(int node, int parent, int top, int down, int sum, vector<int> &path, vector<int> &nums, int twice)
  {
    int prev = trace[nums[node]];
    trace[nums[node]] = down;
    // 1. Update sliding window
    while (top <= min(prev, twice))
    {
      sum -= path[top++];
    }
    // Update 0322: path change to record prefix sum and then sum = path[depth] - path[top-1] can speed up the runtime
    if (prev != -1)
    {
      twice = max(twice, prev); // update twice
    }

    // 2. Update the output if length is greater
    if (sum > longSpecial)
    {
      longSpecial = sum;
      minCount = down - top + 1;
    }
    else if (sum == longSpecial)
    {
      minCount = min(minCount, down - top + 1);
    }

    // 3. Go to next stage
    for (auto &next : adj[node])
    {
      if (next.first != parent)
      {
        path.push_back(next.second);
        DFS(next.first, node, top, down + 1, sum + next.second, path, nums, twice);
        path.pop_back();
      }
    }
    trace[nums[node]] = prev;
  }
};