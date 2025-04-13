import java.util.HashMap;

public class Solution {
    final int MOD = 1000000007;
    int base;
    int[] digits;
    HashMap<String, Integer> memo;

    public int countNumbers(String l, String r, int b) {
        base = b;
        int fr = f(r);
        int fl = l.equals("0") ? 0 : f(new java.math.BigInteger(l).subtract(java.math.BigInteger.ONE).toString());
        return (fr - fl + MOD) % MOD;
    }

    private int f(String x) {
        digits = dec2base(new java.math.BigInteger(x), base);
        memo = new HashMap<>();
        return dp(0, -1, true, false);
    }

    private int[] dec2base(java.math.BigInteger num, int base) {
        if (num.equals(java.math.BigInteger.ZERO))
            return new int[] { 0 };
        ArrayList<Integer> list = new ArrayList<>();
        while (num.compareTo(java.math.BigInteger.ZERO) > 0) {
            list.add(num.mod(java.math.BigInteger.valueOf(base)).intValue());
            num = num.divide(java.math.BigInteger.valueOf(base));
        }
        Collections.reverse(list);
        return list.stream().mapToInt(i -> i).toArray();
    }

    private int dp(int i, int last, boolean tight, boolean started) {
        if (i == digits.length)
            return 1;

        String key = i + "," + last + "," + tight + "," + started;
        if (memo.containsKey(key))
            return memo.get(key);

        int up = tight ? digits[i] : base - 1;
        int ways = 0;

        for (int d = 0; d <= up; d++) {
            boolean newTight = tight && (d == up);
            if (!started) {
                if (d == 0) {
                    ways = (ways + dp(i + 1, -1, newTight, false)) % MOD;
                } else {
                    ways = (ways + dp(i + 1, d, newTight, true)) % MOD;
                }
            } else {
                if (d < last)
                    continue;
                ways = (ways + dp(i + 1, d, newTight, true)) % MOD;
            }
        }

        memo.put(key, ways);
        return ways;
    }
}
