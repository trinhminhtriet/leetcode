class Solution {
        public long countNonDecreasingSubarrays(int[] A, long k) {
        // Reverse the array
        int n = A.length;
        for (int i = 0; i < n / 2; ++i) {
            int temp = A[i];
            A[i] = A[n - 1 - i];
            A[n - 1 - i] = temp;
        }

        long res = 0;
        Deque<Integer> q = new ArrayDeque<>();
        for (int j = 0, i = 0; j < A.length; ++j) {
            while (!q.isEmpty() && A[q.peekLast()] < A[j]) {
                int r = q.pollLast();
                int l = q.isEmpty() ? i - 1 : q.peekLast();
                k -= 1L * (r - l) * (A[j] - A[r]);
            }
            q.addLast(j);
            while (k < 0) {
                k += A[q.peekFirst()] - A[i];
                if (q.peekFirst() == i) {
                    q.pollFirst();
                }
                ++i;
            }
            res += j - i + 1;
        }
        return res;
    }
}