class Solution {
    static int MOD = 1000000007;
    static int MAX = 100000;
    
    static long[] fact = new long[MAX + 1];
    static long[] invFact = new long[MAX + 1];
    
    static {
        fact[0] = 1L;
        for (int i = 1; i <= MAX; i++) {
            fact[i] = fact[i - 1] * i % MOD;
        }
        invFact[MAX] = modInverse(fact[MAX], MOD);
        for (int i = MAX - 1; i >= 0; i--) {
            invFact[i] = invFact[i + 1] * (i + 1) % MOD;
        }
    }
    
    public static long modInverse(long a, int m) {
        return fastPow(a, m - 2, m);
    }
    
    public static long fastPow(long base, long exp, long mod) {
        long result = 1L;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exp >>= 1;
        }
        return result;
    }
    
    public static long binom(int n, int r) {
        if (r < 0 || r > n) return 0;
        return fact[n] * ((invFact[r] * invFact[n - r]) % MOD) % MOD;
    }
    
    public int distanceSum(int m, int n, int k) {
        int M = m * n;
        
        long ways = binom(M - 2, k - 2);
        
        long mm = (long) m % MOD;
        long nn = (long) n % MOD;
        
        long n2 = (nn * nn) % MOD;
        long m2 = (mm * mm) % MOD;
        
        long term1 = (n2 * mm) % MOD;
        long m2Minus1 = (m2 - 1 + MOD) % MOD;
        term1 = (term1 * m2Minus1) % MOD;
        
        long term2 = (m2 * nn) % MOD;
        long n2Minus1 = (n2 - 1 + MOD) % MOD;
        term2 = (term2 * n2Minus1) % MOD;
        
        long sumD = (term1 + term2) % MOD;
        
        long inv6 = modInverse(6, MOD);
        sumD = (sumD * inv6) % MOD;
        
        long ans = (sumD * ways) % MOD;
        
        return (int) ans;
    }
}