class Solution {
    public int minTime(int n, int[][] edges) {
        boolean[] seen = new boolean[n];
        PriorityQueue<int[]> h = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        h.add(new int[] { 0, 0 });
        List<List<int[]>> G = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            G.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            G.get(edge[0]).add(new int[] { edge[1], edge[2], edge[3] });
        }
        while (!h.isEmpty()) {
            int[] cur = h.poll();
            int t = cur[0], u = cur[1];
            if (u == n - 1) {
                return t;
            }
            if (seen[u]) {
                continue;
            }
            seen[u] = true;
            for (int[] edge : G.get(u)) {
                int v = edge[0], s = edge[1], e = edge[2];
                if (t <= e) {
                    int t2 = Math.max(s, t) + 1;
                    if (!seen[v]) {
                        h.add(new int[] { t2, v });
                    }
                }
            }
        }
        return -1;
    }
}
