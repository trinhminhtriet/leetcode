class Solution {
    public String smallestPalindrome(String inputStr, int K) {
        int[] frequency = new int[26];
        for (int i = 0; i < inputStr.length(); i++) {
            char ch = inputStr.charAt(i);
            frequency[ch - 'a']++;
        }
        char mid = 0;
        for (int i = 0; i < 26; i++) {
            if (frequency[i] % 2 == 1) {
                mid = (char) ('a' + i);
                frequency[i]--;
                break;
            }
        }
        int[] halfFreq = new int[26];
        int halfLength = 0;
        for (int i = 0; i < 26; i++) {
            halfFreq[i] = frequency[i] / 2;
            halfLength += halfFreq[i];
        }
        long totalPerms = multinomial(halfFreq);
        if (K > totalPerms)
            return "";
        StringBuilder firstHalfBuilder = new StringBuilder();
        for (int pos = 0; pos < halfLength; pos++) {
            for (int c = 0; c < 26; c++) {
                if (halfFreq[c] > 0) {
                    halfFreq[c]--;
                    long perms = multinomial(halfFreq);
                    if (perms >= K) {
                        firstHalfBuilder.append((char) ('a' + c));
                        break;
                    } else {
                        K -= perms;
                        halfFreq[c]++;
                    }
                }
            }
        }
        String firstHalf = firstHalfBuilder.toString();
        String revHalf = new StringBuilder(firstHalf).reverse().toString();
        String result;
        if (mid == 0) {
            result = firstHalf + revHalf;
        } else {
            String midStr = new String(new char[] { mid });
            result = firstHalf + midStr + revHalf;
        }
        return result;
    }

    static long maxK = 1000001;

    public long multinomial(int[] counts) {
        int tot = 0;
        for (int cnt : counts) {
            tot += cnt;
        }
        long res = 1;
        for (int i = 0; i < 26; i++) {
            int cnt = counts[i];
            res = res * binom(tot, cnt);
            if (res >= maxK)
                return maxK;
            tot -= cnt;
        }
        return res;
    }

    public long binom(int n, int k) {
        if (k > n)
            return 0;
        if (k > n - k)
            k = n - k;
        long result = 1;
        for (int i = 1; i <= k; i++) {
            result = result * (n - i + 1) / i;
            if (result >= maxK)
                return maxK;
        }
        return result;
    }
}
