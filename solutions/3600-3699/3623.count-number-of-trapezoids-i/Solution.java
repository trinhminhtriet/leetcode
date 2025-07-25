class Solution {
    public int countTrapezoids(int[][] points) {
        int n = points.length, MOD = 1000000007;
        Arrays.sort(points, Comparator.comparingInt(a -> a[1]));
        long res = 0, total = 0;
        for (int i = 0, j; i < n; i = j){
            j = i + 1;
            while (j < n && points[i][1] == points[j][1]){
                j++;
            }
            long count = j - i;
            long lines = count * (count - 1) / 2;
            res = (res + total * lines) % MOD;
            total = (total + lines) % MOD;
        }
        return (int)res;
    }
}
