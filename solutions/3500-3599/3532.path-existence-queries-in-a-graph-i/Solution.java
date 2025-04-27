class Solution {
    public boolean[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        List<Integer> cuts = new ArrayList<>();
        cuts.add(0);
        for (int i = 1; i < n; ++i) {
            if (nums[i] - nums[i - 1] > maxDiff)
                cuts.add(i);
        }
        boolean[] res = new boolean[queries.length];
        for (int i = 0; i < queries.length; ++i) {
            int x = Collections.binarySearch(cuts, queries[i][0] + 1);
            int y = Collections.binarySearch(cuts, queries[i][1] + 1);
            x = x < 0 ? -x - 1 : x;
            y = y < 0 ? -y - 1 : y;
            res[i] = x == y;
        }
        return res;
    }
}
