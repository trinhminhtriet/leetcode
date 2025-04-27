class Solution {
    public static int[] concatenatedDivisibility(int[] nums, int k) {
        int n = nums.length;
        int total = (1 << n) - 1;

        int[] multiplicationWithLen = new int[n];
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            int digits = (x == 0 ? 1 : (int) Math.log10(x) + 1);
            long m = 1;
            for (int d = 0; d < digits; d++)
                m = (m * 10) % k;
            multiplicationWithLen[i] = (int) m;
        }

        Map<Long, List<Integer>> dp = new HashMap<>();
        List<Integer> list = solve(0, 0, n, k, total, nums, multiplicationWithLen, dp);
        if (list == null)
            return new int[0];

        int[] ans = new int[n];
        for (int i = 0; i < n; i++)
            ans[i] = list.get(i);
        return ans;
    }

    private static List<Integer> solve(int mask, int rem, int n, int k, int total, int[] nums,
            int[] multiplicationWithLen,
            Map<Long, List<Integer>> dp) {
        if (mask == total)
            return rem == 0 ? new ArrayList<>() : null;
        long key = mask * 100L + rem;
        if (dp.containsKey(key))
            return dp.get(key);

        List<Integer> best = null;
        for (int i = 0; i < n; i++) {
            int bit = 1 << i;
            if ((mask & bit) != 0)
                continue;

            int newRem;
            if (mask == 0)
                newRem = nums[i] % k;
            else
                newRem = (int) ((rem * multiplicationWithLen[i] + nums[i]) % k);

            List<Integer> tail = solve(mask | bit, newRem, n, k, total, nums, multiplicationWithLen, dp);
            if (tail != null) {
                List<Integer> cand = new ArrayList<>();
                cand.add(nums[i]);
                cand.addAll(tail);
                if (best == null || compare(cand, best))
                    best = cand;
            }
        }

        dp.put(key, best);
        return best;
    }

    private static boolean compare(List<Integer> a, List<Integer> b) {
        int n = a.size();
        for (int i = 0; i < n; i++) {
            if (a.get(i) < b.get(i))
                return true;
            else if (a.get(i) > b.get(i))
                return false;
        }
        return true;
    }
}
