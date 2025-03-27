class Solution {
  private static final int MOD = 1000000007;
  private static final int[] power = new int[501];

  static {
    power[0] = 1;
    for (int i = 1; i <= 500; i++) {
      power[i] = (int) (((long) 31 * power[i - 1]) % MOD);
    }
  }

  private int rollingHash(String str) {
    long h = 0;
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '0')
        continue;
      h = (h + (long) power[i] * (str.charAt(i) - 'a' + 1)) % MOD;
    }
    return (int) h;
  }

  private int with(int hash, int pos, char c) {
    return (int) ((hash + (long) power[pos] * (c - 'a' + 1)) % MOD);
  }

  public String generateString(String str1, String str2) {
    int n = str1.length();
    int m = str2.length();
    int sz = n + m - 1;

    // Initialize result string with '0's
    char[] res = new char[sz];
    java.util.Arrays.fill(res, '0');

    // Copy str2 where str1 has 'T'
    for (int i = 0; i < n; i++) {
      if (str1.charAt(i) == 'T') {
        for (int j = 0; j < m && i + j < sz; j++) {
          res[i + j] = str2.charAt(j);
        }
      }
    }

    // Check initial conditions
    for (int i = 0; i < n; i++) {
      String substr = new String(res, i, Math.min(m, sz - i));
      if ((str1.charAt(i) == 'T' && !substr.equals(str2)) ||
          (str1.charAt(i) == 'F' && substr.equals(str2))) {
        return "";
      }
    }

    java.util.Deque<int[]> falseList = new java.util.ArrayDeque<>();
    int str2Hash = rollingHash(str2);

    for (int idx = 0; idx < sz; idx++) {
      if (idx < n && str1.charAt(idx) == 'F') {
        String substr = new String(res, idx, Math.min(m, sz - idx));
        falseList.add(new int[] { idx, rollingHash(substr) });
      }

      while (!falseList.isEmpty() && Math.abs(falseList.peekFirst()[0] - idx) >= m) {
        falseList.pollFirst();
      }

      if (res[idx] == '0') {
        outer: for (char ch = 'a'; ch <= 'z'; ch++) {
          for (int[] falseData : falseList) {
            int falseIndex = falseData[0];
            int h = falseData[1];
            if (with(h, idx - falseIndex, ch) == str2Hash) {
              continue outer;
            }
          }

          // Update all hashes and set character
          for (int[] falseData : falseList) {
            int falseIndex = falseData[0];
            falseData[1] = with(falseData[1], idx - falseIndex, ch);
          }
          res[idx] = ch;
          break;
        }
      }
    }

    return new String(res);
  }
}