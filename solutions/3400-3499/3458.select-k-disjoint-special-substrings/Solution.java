class Solution {
  public boolean maxSubstringLength(String s, int k) {
    int n = s.length();
    int[] first = new int[26];
    int[] last = new int[26];
    Arrays.fill(first, -1);
    Arrays.fill(last, -1);

    // Record first and last occurrences of each character
    for (int i = 0; i < n; i++) {
      int c = s.charAt(i) - 'a';
      if (first[c] == -1)
        first[c] = i;
      last[c] = i;
    }

    // Use ArrayList instead of vector for intervals
    List<int[]> intervals = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      int c = s.charAt(i) - 'a';
      if (i != first[c])
        continue;

      int j = last[c];
      for (int m = i; m <= j; m++) {
        j = Math.max(j, last[s.charAt(m) - 'a']);
      }

      boolean flag = true;
      for (int m = i; m <= j; m++) {
        if (first[s.charAt(m) - 'a'] < i) {
          flag = false;
          break;
        }
      }
      if (!flag)
        continue;

      if (i == 0 && j == n - 1)
        continue;

      intervals.add(new int[] { i, j });
    }

    // Sort intervals based on end points
    Collections.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));

    int cnt = 0;
    int currend = -1;
    for (int[] interval : intervals) {
      if (interval[0] > currend) {
        cnt++;
        currend = interval[1];
      }
    }

    return cnt >= k; // Fixed: changed SDL to k
  }
}