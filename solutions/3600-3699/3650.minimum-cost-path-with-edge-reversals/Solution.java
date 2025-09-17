class Solution {
    private static final int INF = Integer.MAX_VALUE / 2;
    
    public int minCost(int n, int[][] edges) {
        var graph = new ArrayList<List<int[]>>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            int from = edge[0], to = edge[1], cost = edge[2];
            graph.get(from).add(new int[]{to, cost});
            graph.get(to).add(new int[]{from, cost * 2});
        }
        
        int[] d = new int[n];
        Arrays.fill(d, INF);
        d[0] = 0;
        
        var q = new PriorityQueue<int[]>((p1, p2) -> 
            p1[0] == p2[0] ? Integer.compare(p1[1], p2[1]) : Integer.compare(p1[0], p2[0]));
        q.offer(new int[]{0, 0});
        
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int distance = curr[0], from = curr[1];
            if (distance > d[from]) continue;
            
            for (int[] edge : graph.get(from)) {
                int to = edge[0], cost = edge[1];
                if (d[to] > d[from] + cost) {
                    d[to] = d[from] + cost;
                    q.offer(new int[]{d[to], to});
                }
            }
        }
        
        return d[n - 1] == INF ? -1 : d[n - 1];
    }
}