class Solution {
    public long maxAlternatingSum(int[] a) {
        int n = a.length;
        long sum = 0;
        int i = 0, j = n - 1;
        for (int k = 0; k < n; k++) {
            a[k] = a[k] * a[k];
        }
        java.util.Arrays.sort(a);
        java.util.List<Long> ans = new java.util.ArrayList<>();
        while (i < j) {
            ans.add((long)a[j]);
            ans.add((long)a[i]);
            j--;
            i++;
        }
        if ((n & 1) == 1) ans.add((long)a[n / 2]);
        for (int k = 0; k < n; k++) {
            if (k % 2 == 0) sum += ans.get(k);
            else sum -= ans.get(k);
        }
        return sum;
    }
}