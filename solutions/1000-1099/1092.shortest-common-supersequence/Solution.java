class Solution {
    public String shortestCommonSupersequence(String str1, String str2) {
        int str1Length = str1.length(), str2Length = str2.length();
        int[][] dp = new int[str1Length + 1][str2Length + 1];

        for (int i = 1; i <= str1Length; ++i) {
            for (int j = 1; j <= str2Length; ++j) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        StringBuilder shortestSupersequence = new StringBuilder();
        int i = str1Length, j = str2Length;

        while (i > 0 || j > 0) {
            if (i == 0) {
                shortestSupersequence.append(str2.charAt(--j));
            } else if (j == 0) {
                shortestSupersequence.append(str1.charAt(--i));
            } else {
                if (dp[i][j] == dp[i - 1][j - 1] + 1) {
                    shortestSupersequence.append(str1.charAt(--i));
                    --j;
                } else if (dp[i][j] == dp[i - 1][j]) {
                    shortestSupersequence.append(str1.charAt(--i));
                } else {
                    shortestSupersequence.append(str2.charAt(--j));
                }
            }
        }

        return shortestSupersequence.reverse().toString();
    }
}
