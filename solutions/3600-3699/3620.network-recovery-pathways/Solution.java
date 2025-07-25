class Solution {
    private long dijkstra(int src, int target, List<List<int[]>> adj, int n) {
        long INF = Long.MAX_VALUE / 4;
        long[] dist = new long[n];
        Arrays.fill(dist, INF);
        dist[src] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        pq.offer(new long[]{0, src});

        while (!pq.isEmpty()) {
            long[] top = pq.poll();
            long d = top[0]; int u = (int) top[1];
            if (d > dist[u]) continue;
            if (u == target) return d;

            for (int[] edge : adj.get(u)) {
                int v = edge[0], w = edge[1];
                if (dist[v] > d + w) {
                    dist[v] = d + w;
                    pq.offer(new long[]{dist[v], v});
                }
            }
        }
        return INF;
    }

    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        long l = 0, h = (int) 1e9, best = -1;

        while (l <= h) {
            long mid = l + (h - l) / 2;
            List<List<int[]>> adj = new ArrayList<>();
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

            for (int[] e : edges) {
                int u = e[0], v = e[1], c = e[2];
                if (c >= mid && online[u] && online[v]) {
                    adj.get(u).add(new int[]{v, c});
                }
            }

            long dist = dijkstra(0, n - 1, adj, n);
            if (dist <= k) {
                best = mid;
                l = mid + 1;
            } else {
                h = mid - 1;
            }
        }
        return (int) best;
    }
}
