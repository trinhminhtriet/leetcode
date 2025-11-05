class Solution {
    public long minimumTime(int[] d, int[] r) {
        long low = 0;
        long temp = ((long)d[0] + (long)d[1]) * (long)Math.max(r[0], r[1]);
        long high = Math.max(100L, temp);
        while (low <= high) {
            long mid = low + (high - low) / 2;
            long x1 = mid - mid / r[0];
            long x2 = mid - mid / r[1];
            long gcdd = gcd(r[0], r[1]);
            long x = (r[0] / gcdd) * (long)r[1];
            long x3 = mid - (mid / r[0] + mid / r[1] - mid / x);
            if (x1 >= d[0] && x2 >= d[1] && x1 + x2 - x3 >= (long)d[0] + d[1]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
    private long gcd(long a, long b) {
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}