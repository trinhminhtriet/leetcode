import java.util.*;

class Solution {
    private long[] digits;
    private int lenN;
    private Map<String, Long> memo;

    public long countNoZeroPairs(long n) {
        String s = Long.toString(n);
        lenN = s.length();

        digits = new long[lenN];
        for (int i = 0; i < lenN; i++) {
            digits[lenN - 1 - i] = s.charAt(i) - '0'; // reverse digits
        }

        memo = new HashMap<>();
        long totalPairs = 0;
        for (int lenA = 1; lenA <= lenN; lenA++) {
            for (int lenB = 1; lenB <= lenN; lenB++) {
                totalPairs += solve(0, 0, lenA, lenB);
            }
        }

        return totalPairs;
    }

    private long solve(int pos, long carry, int lenA, int lenB) {
        if (pos == lenN) {
            return carry == 0 ? 1 : 0;
        }

        String key = pos + "," + carry + "," + lenA + "," + lenB;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        long numWays = 0;
        long[] rangeA = pos < lenA ? new long[]{1,2,3,4,5,6,7,8,9} : new long[]{0};
        long[] rangeB = pos < lenB ? new long[]{1,2,3,4,5,6,7,8,9} : new long[]{0};

        for (long da : rangeA) {
            for (long db : rangeB) {
                long sum = da + db + carry;
                if (sum % 10 == digits[pos]) {
                    numWays += solve(pos + 1, sum / 10, lenA, lenB);
                }
            }
        }

        memo.put(key, numWays);
        return numWays;
    }
}
