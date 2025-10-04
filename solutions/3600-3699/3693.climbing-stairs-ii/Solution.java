class Solution {
    private static final int[] jumps = new int[]{1, 2, 3};
    public int climbStairs(int n, int[] costs) {
        int[] minCost = new int[n + 1];
        Arrays.fill(minCost, Integer.MAX_VALUE);
        minCost[0] = 0;


        for (int i = 1; i <= n; i++) {
            for(int jump : jumps) {
                int startIndex = i - jump;
                if (startIndex >= 0) {
                    minCost[i] = Math.min(minCost[i], minCost[startIndex] + costs[i - 1] + jump * jump);
                }
            }
        }

        return minCost[n];
    }
}