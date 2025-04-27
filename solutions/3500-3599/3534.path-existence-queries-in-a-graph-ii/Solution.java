class Solution {
    private static final int INF = Integer.MAX_VALUE;

    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        int[][] vi = new int[n][2];
        for (int i = 0; i < n; i++) {
            vi[i][0] = nums[i];
            vi[i][1] = i;
        }

        java.util.Arrays.sort(vi, (a, b) -> Integer.compare(a[0], b[0]));

        int[] n2i = new int[n];
        int[] vals = new int[n];
        for (int i = 0; i < n; i++) {
            vals[i] = vi[i][0];
            n2i[vi[i][1]] = i;
        }

        int h = 0;
        while ((1 << h) <= n) {
            h++;
        }
        h++;

        int[][] jumps = new int[n][h];
        for (int i = 0; i < n; i++) {
            java.util.Arrays.fill(jumps[i], -1);
        }

        int i = 0, j = 0;
        while (i < n) {
            while (j + 1 < n && vals[j + 1] - vals[i] <= maxDiff) {
                j++;
            }
            jumps[i][0] = j;
            i++;
        }

        for (int k = 1; k < h; k++) {
            for (int p = 0; p < n; p++) {
                int prev = jumps[p][k - 1];
                if (prev != -1) {
                    jumps[p][k] = jumps[prev][k - 1];
                }
            }
        }

        int[] res = new int[queries.length];

        for (int qi = 0; qi < queries.length; qi++) {
            int a = n2i[queries[qi][0]];
            int b = n2i[queries[qi][1]];
            if (a > b) {
                int tmp = a;
                a = b;
                b = tmp;
            }
            int cur = query(a, b, jumps, h);
            res[qi] = (cur < INF) ? cur : -1;
        }

        return res;
    }

    private int query(int a, int b, int[][] jumps, int h) {
        if (a == b) return 0;
        if (jumps[a][0] >= b) return 1;

        for (int j = 0; j < h; j++) {
            if (jumps[a][j] >= b) {
                if (j == 0) {
                    return 1;
                }
                j--;
                int next = jumps[a][j];
                int subQuery = query(next, b, jumps, h);
                if (subQuery == INF) return INF;
                return (1 << j) + subQuery;
            }
        }

        return INF;
    }
}
