public class Solution {
    private int m, n, M = 1000000007;
    private char[] s1, s2, evil;
    private int[][][][] dp;
    private int[] pi, p;
    
    public int findGoodStrings(int n, String s1, String s2, String evil) {
        this.s1 = s1.toCharArray();
        this.s2 = s2.toCharArray();
        this.evil = evil.toCharArray();
        this.m = evil.length();
        this.n = n;
        dp = new int[n][m+1][2][2];
        for (int[][][] d : dp)
            for (int[][] e : d)
                for (int[] f : e) Arrays.fill(f, -1);

        pi = new int[m];
        char[] pat = evil.toCharArray();

        int length = 0, i = 1;
        while (i < m) {
            if (pat[i] == pat[length]) {
                pi[i++] = ++length;
            } else if (length > 0) {
                length = pi[length - 1];
            } else {
                pi[i++] = 0;
            }
        }

        this.p = new int[n+1];
        p[0] = 1;
        for (i = 1; i <= n; i++) {
            p[i] = 26L * p[i-1] % M;
        }

        return f(0, 0, 1, 1);
    }
    
    private int f(int i, int j, int b1, int b2) {
        if (j == m) return 0;
        if (i == n) return 1;
        if (dp[i][j][b1][b2] != -1) return dp[i][j][b1][b2];

        long res = 0;
        int lo = b1 > 0 ? s1[i] - 'a' : 0, hi = b2 > 0 ? s2[i] - 'a' : 25;
        for (int c = lo; c <= hi; c++) {
            int ni = i+1, nj = j;
            while (nj > 0 && evil[nj] != c + 'a') {
                nj = pi[nj-1];
            }
            if (c == evil[nj] - 'a') nj++;
            res = (res + f(ni, nj, b1 & (c == lo ? 1 : 0), b2 & (c == hi ? 1 : 0))) % M;
        }

        return dp[i][j][b1][b2] = (int) res;
    }
}