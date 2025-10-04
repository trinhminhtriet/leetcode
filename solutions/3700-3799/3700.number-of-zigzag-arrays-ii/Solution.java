class Solution {
    private final long MOD = 1000000007L;

    private int[][] mulMat(int[][] A, int[][] B, int[][] C) {
        int n = A.length;
        for (int i = 0; i < n; ++i) Arrays.fill(C[i], 0);

        for (int i = 0; i < n; ++i) {
            for (int k = 0; k < n; ++k) {
                int aik = A[i][k];
                if (aik == 0) continue;
                long a = aik;
                for (int j = 0; j < n; ++j) {
                    int bkj = B[k][j];
                    if (bkj == 0) continue;
                    C[i][j] = (int) ((C[i][j] + (a * bkj) % MOD) % MOD);
                }
            }
        }
        return C;
    }

    private int[] mulMatVec(int[][] A, int[] v, int[] res) {
        int n = A.length;
        Arrays.fill(res, 0);
        for (int i = 0; i < n; ++i) {
            long sum = 0;
            for (int j = 0; j < n; ++j) {
                if (A[i][j] == 0 || v[j] == 0) continue;
                sum += (long) A[i][j] * v[j];
                if (sum >= MOD * 2) sum %= MOD;
            }
            res[i] = (int) (sum % MOD);
        }
        return res;
    }

    public int zigZagArrays(long n, int l, int r) {
        int m = r - l + 1;
        if (m <= 0) return 0;
        if (n == 1) return (int)(m % MOD);

        int sz = 2 * m;
        int[][] M = new int[sz][sz];

        for (int v = 1; v <= m; ++v) {
            for (int u = v + 1; u <= m; ++u) {
                M[m + v - 1][u - 1] = 1;
            }
        }

        for (int v = 1; v <= m; ++v) {
            for (int u = 1; u < v; ++u) {
                M[v - 1][m + u - 1] = 1;
            }
        }

        int[] V_2 = new int[sz];
        for (int v = 1; v <= m; ++v) {
            V_2[v - 1] = v - 1;
            V_2[m + v - 1] = m - v;
        }

        if (n == 2) {
            long ans = 0;
            for (int val : V_2) ans = (ans + val) % MOD;
            return (int)ans;
        }

        long exp = n - 2; 
        int[] resVec = V_2;
        int[][] base = M;
        
        int[][] tempMat = new int[sz][sz];
        int[] tempVec = new int[sz];

        while (exp > 0) {
            if ((exp & 1) == 1) {
                mulMatVec(base, resVec, tempVec);
                System.arraycopy(tempVec, 0, resVec, 0, sz);
            }
            exp >>= 1;
            if (exp > 0) {
                mulMat(base, base, tempMat);
                
                int[][] swap = base;
                base = tempMat;
                tempMat = swap;
            }
        }

        long ans = 0;
        for (int val : resVec) ans = (ans + val) % MOD;
        return (int)ans;
    }
}