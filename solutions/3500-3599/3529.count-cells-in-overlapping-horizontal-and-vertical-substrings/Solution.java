import java.util.*;

class Solution {
    public int countCells(char[][] grid, String pattern) {
        int n = grid.length, m = grid[0].length, sz = pattern.length();

        StringBuilder horizontalString = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                horizontalString.append(grid[i][j]);
            }
        }

        StringBuilder verticalString = new StringBuilder();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                verticalString.append(grid[j][i]);
            }
        }

        String horizontalPatternString = pattern + "#" + horizontalString;
        int[] lps1 = getLPS(horizontalPatternString);

        String verticalPatternString = pattern + "#" + verticalString;
        int[] lps2 = getLPS(verticalPatternString);

        int sz1 = lps1.length;
        int[] scan1 = new int[sz1 + 1];
        for (int i = 0; i < sz1; i++) {
            if (lps1[i] == sz) {
                scan1[i - sz + 1]++;
                scan1[i + 1]--;
            }
        }

        int sz2 = lps2.length;
        int[] scan2 = new int[sz2 + 1];
        for (int i = 0; i < sz2; i++) {
            if (lps2[i] == sz) {
                scan2[i - sz + 1]++;
                scan2[i + 1]--;
            }
        }

        boolean[][] visited = new boolean[n][m];

        int count1 = 0;
        for (int i = 0; i < sz1; i++) {
            count1 += scan1[i];
            if (count1 > 0) {
                int idx = i - sz - 1;
                if (idx >= 0) {
                    int row = idx / m;
                    int column = idx % m;
                    if (row >= 0 && row < n && column >= 0 && column < m) {
                        visited[row][column] = true;
                    }
                }
            }
        }

        int count2 = 0;
        int ans = 0;
        for (int i = 0; i < sz2; i++) {
            count2 += scan2[i];
            if (count2 > 0) {
                int idx = i - sz - 1;
                if (idx >= 0) {
                    int row = idx % n;
                    int column = idx / n;
                    if (row >= 0 && row < n && column >= 0 && column < m) {
                        if (visited[row][column]) {
                            ans++;
                        }
                    }
                }
            }
        }

        return ans;
    }

    private int[] getLPS(String s) {
        int n = s.length();
        int[] lps = new int[n];
        for (int i = 1; i < n; i++) {
            int j = lps[i - 1];
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = lps[j - 1];
            }
            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }
            lps[i] = j;
        }
        return lps;
    }
}
