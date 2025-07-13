class Solution {
    static public List<Integer> findCoins(int[] numWays) {
        int n = numWays.length;
        int[] dp = new int[n + 1];
        ArrayList<Integer> coins = new ArrayList<>();
        dp[0] = 1;

        for (int i = 0; i < n; i++) {
            int amount = i + 1, ways = numWays[i];
            if (ways > 0 && dp[amount] == ways - 1) {
                coins.add(amount);
                for (int coin = amount; coin <= n; coin++) {
                    dp[coin] += dp[coin - amount];
                }
            }

            if (dp[amount] != ways)
                return new ArrayList<>();
        }
        return coins;
    }
}
