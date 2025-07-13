class Solution {
    private int[] parent;

    private int find(int i) {
        if (parent[i] == i) {
            return i;
        }
        return parent[i] = find(parent[i]);
    }

    private boolean unite(int i, int j) {
        i = find(i);
        j = find(j);
        if (i != j) {
            parent[i] = j;
            return true;
        }
        return false;
    }

    public int minTime(int n, int[][] edges, int k) {
        if (edges.length == 0) {
            return 0;
        }
        Arrays.sort(edges, (a, b) -> Integer.compare(b[2], a[2]));
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        int count = n;
        for (int[] e : edges) {
            int u = e[0], v = e[1], t = e[2];
            if (unite(u, v)) {
                count--;
            }
            if (count < k) {
                return t;
            }
        }

        return 0;
    }
}
