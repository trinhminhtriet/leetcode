import java.util.*;

class Solution {
  private int longSpecial = 0;
  private int minCount = 1;
  private int n;
  private List<List<int[]>> adj;
  private int[] trace;

  public int[] longestSpecialPath(int[][] edges, int[] nums) {
    n = edges.length + 1;
    adj = new ArrayList<>(n);
    trace = new int[50001];
    Arrays.fill(trace, -1);

    for (int i = 0; i < n; i++) {
      adj.add(new ArrayList<>());
    }

    for (int[] e : edges) {
      adj.get(e[0]).add(new int[] { e[1], e[2] });
      adj.get(e[1]).add(new int[] { e[0], e[2] });
    }

    List<Integer> path = new ArrayList<>();
    DFS(0, -1, 0, 0, 0, path, nums, -1);
    return new int[] { longSpecial, minCount };
  }

  private void DFS(int node, int parent, int top, int down, int sum, List<Integer> path, int[] nums, int twice) {
    int prev = trace[nums[node]];
    trace[nums[node]] = down;

    while (top <= Math.min(prev, twice)) {
      sum -= path.get(top++);
    }

    if (prev != -1) {
      twice = Math.max(twice, prev);
    }

    if (sum > longSpecial) {
      longSpecial = sum;
      minCount = down - top + 1;
    } else if (sum == longSpecial) {
      minCount = Math.min(minCount, down - top + 1);
    }

    for (int[] next : adj.get(node)) {
      if (next[0] != parent) {
        path.add(next[1]);
        DFS(next[0], node, top, down + 1, sum + next[1], path, nums, twice);
        path.remove(path.size() - 1);
      }
    }

    trace[nums[node]] = prev;
  }
}
