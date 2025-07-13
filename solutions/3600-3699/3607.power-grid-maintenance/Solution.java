class DSU {
    int[] parent;

    public DSU(int n) {
        parent = new int[n + 1];
        for (int i = 0; i <= n; i++)
            parent[i] = i;
    }

    public int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]); // path compression
        return parent[x];
    }

    public boolean union(int x, int y) {
        int px = find(x), py = find(y);
        if (px == py)
            return false;
        parent[py] = px;
        return true;
    }
}

class Solution {
    public int[] processQueries(int c, int[][] connections, int[][] queries) {
        DSU dsu = new DSU(c);
        boolean[] online = new boolean[c + 1];
        Arrays.fill(online, true);

        for (int[] conn : connections)
            dsu.union(conn[0], conn[1]);

        Map<Integer, PriorityQueue<Integer>> componentHeap = new HashMap<>();
        for (int station = 1; station <= c; station++) {
            int root = dsu.find(station);
            componentHeap.putIfAbsent(root, new PriorityQueue<>());
            componentHeap.get(root).offer(station);
        }

        List<Integer> result = new ArrayList<>();

        for (int[] query : queries) {
            int type = query[0], x = query[1];

            if (type == 2) {
                online[x] = false;
            } else {
                if (online[x]) {
                    result.add(x);
                } else {
                    int root = dsu.find(x);
                    PriorityQueue<Integer> heap = componentHeap.get(root);

                    while (heap != null && !heap.isEmpty() && !online[heap.peek()]) {
                        heap.poll();
                    }

                    result.add((heap == null || heap.isEmpty()) ? -1 : heap.peek());
                }
            }
        }

        int[] ans = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            ans[i] = result.get(i);
        }
        return ans;
    }
}
