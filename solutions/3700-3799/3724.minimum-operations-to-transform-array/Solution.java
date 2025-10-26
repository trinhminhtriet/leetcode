class Solution {
        public long minOperations(int[] A, int[] B) {
        int n = A.length, v = B[n], last = 100000;
        long res = 0;
        for (int i = 0; i < n; ++i) {
            int a = A[i], b = B[i];
            res += Math.abs(a - b);
            if ((a <= v && v <= b) || (b <= v && v <= a))
                last = 0;
            else {
                int d = Math.min(Math.abs(a - v), Math.abs(b - v));
                last = Math.min(last, d);
            }
        }
        return res + last + 1;
    }
}