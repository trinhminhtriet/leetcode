import java.util.*;

class Solution {
    public boolean[] findAnswer(int n, int[][] edges) {
        boolean[] res = new boolean[edges.length];
        boolean[] visited = new boolean[n];

        List<List<int[]>> al = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            al.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            al.get(edge[0]).add(new int[]{edge[1], i});
            al.get(edge[1]).add(new int[]{edge[0], i});
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        pq.offer(new int[]{0, 0});
        dist[0] = 0;

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int d = current[0];
            int u = current[1];

            if (d > dist[u]) continue;

            for (int[] neighbor : al.get(u)) {
                int v = neighbor[0];
                int e = neighbor[1];
                int newDist = d + edges[e][2];
                if (newDist < dist[v]) {
                    dist[v] = newDist;
                    pq.offer(new int[]{dist[v], v});
                }
            }
        }

        if (dist[n - 1] == Integer.MAX_VALUE) {
            return res;
        }

        pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        pq.offer(new int[]{dist[n - 1], n - 1});
        visited[n - 1] = true;

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int d = current[0];
            int u = current[1];

            for (int[] neighbor : al.get(u)) {
                int v = neighbor[0];
                int e = neighbor[1];
                if (d - edges[e][2] == dist[v]) {
                    res[e] = true;
                    if (!visited[v]) {
                        visited[v] = true;
                        pq.offer(new int[]{dist[v], v});
                    }
                }
            }
        }

        return res;
    }
}
