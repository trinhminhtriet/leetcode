class Solution {
  private long lcm(long a, long b) {
    return a / gcd(a, b) * b;
  }

  private long gcd(long a, long b) {
    while (b != 0) {
      long temp = b;
      b = a % b;
      a = temp;
    }
    return a;
  }

  public int minimumIncrements(int[] nums, int[] target) {
    int k = target.length;
    HashMap<Long, Long> mpp = new HashMap<>();

    // Generate all possible subsets and their LCMs
    for (long mask = 1; mask < (1L << k); mask++) {
      ArrayList<Long> subset = new ArrayList<>();
      for (int i = 0; i < k; i++) {
        if ((mask & (1L << i)) != 0) {
          subset.add((long) target[i]);
        }
      }
      long currlcm = subset.get(0);
      for (int j = 1; j < subset.size(); j++) {
        currlcm = lcm(currlcm, subset.get(j));
      }
      mpp.put(mask, currlcm);
    }

    long fullmask = (1L << k) - 1;
    long[] dp = new long[1 << k];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;

    // Process each number
    for (int num : nums) {
      ArrayList<long[]> maskcost = new ArrayList<>();
      for (Map.Entry<Long, Long> entry : mpp.entrySet()) {
        long mask = entry.getKey();
        long lcmval = entry.getValue();
        long rm = num % lcmval;
        long cost = (rm == 0) ? 0 : (lcmval - rm);
        maskcost.add(new long[] { mask, cost });
      }

      long[] newdp = dp.clone();
      for (int prevmask = 0; prevmask < (1 << k); prevmask++) {
        if (dp[prevmask] == Integer.MAX_VALUE)
          continue;
        for (long[] mc : maskcost) {
          long mask = mc[0];
          long cost = mc[1];
          int nmask = (int) (prevmask | mask);
          long ncost = dp[prevmask] + cost;
          if (ncost < newdp[nmask]) {
            newdp[nmask] = ncost;
          }
        }
      }

      dp = newdp;
    }

    return dp[(int) fullmask] == Integer.MAX_VALUE ? -1 : (int) dp[(int) fullmask];
  }
}