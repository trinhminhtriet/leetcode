import java.util.HashMap;
import java.util.Map;

class Solution {
    private static final int MIN = -5000;
    private Map<Integer, Map<Integer, Map<Integer, Map<Integer, Integer>>>> dp = new HashMap<>();

    private int recursion(int pos, int currSum, int product, int isOdd, int k, int n, int[] nums, int limit) {
        if (pos == n) {
            return (currSum == k && isOdd != 0 && product <= limit) ? product : MIN;
        }

        if (dp.containsKey(pos) && dp.get(pos).containsKey(currSum)
                && dp.get(pos).get(currSum).containsKey(product)
                && dp.get(pos).get(currSum).get(product).containsKey(isOdd)) {
            return dp.get(pos).get(currSum).get(product).get(isOdd);
        }

        int ans = recursion(pos + 1, currSum, product, isOdd, k, n, nums, limit);
        if (isOdd == 0) {
            ans = Math.max(ans, recursion(pos + 1, currSum + nums[pos], nums[pos], 2, k, n, nums, limit));
        }
        if (isOdd == 1) {
            ans = Math.max(ans, recursion(pos + 1, currSum + nums[pos], Math.min(product * nums[pos], limit + 1), 2, k,
                    n, nums, limit));
        }
        if (isOdd == 2) {
            ans = Math.max(ans, recursion(pos + 1, currSum - nums[pos], Math.min(product * nums[pos], limit + 1), 1, k,
                    n, nums, limit));
        }

        // Initialize the nested maps if they don't exist
        dp.computeIfAbsent(pos, k1 -> new HashMap<>())
                .computeIfAbsent(currSum, k2 -> new HashMap<>())
                .computeIfAbsent(product, k3 -> new HashMap<>())
                .put(isOdd, ans);

        return ans;
    }

    public int maxProduct(int[] nums, int k, int limit) {
        int n = nums.length;

        int sum = 0;
        for (int x : nums)
            sum += x;

        if (k > sum || k < -sum)
            return -1;

        dp.clear();
        int ans = recursion(0, 0, 0, 0, k, n, nums, limit);
        return (ans == MIN) ? -1 : ans;
    }
}
