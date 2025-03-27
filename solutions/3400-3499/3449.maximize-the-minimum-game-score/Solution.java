class Solution {
  public long maxScore(int[] points, int m) {
    int n = points.length;
    if (m < n)
      return 0;

    long left = 1, right = (long) 1e18, ans = 0;

    while (left <= right) {
      long mid = left + (right - left) / 2;
      if (canAchieve(points, mid, m)) {
        ans = mid;
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return ans;
  }

  private boolean canAchieve(int[] points, long val, int m) {
    long total = 0, transfer = 0, skipAdd = 0;
    int n = points.length;

    for (int i = 0; i < n && total <= m; i++) {
      int point = points[i];
      long necessary = (val + point - 1) / point;

      if (transfer >= necessary) {
        transfer = 0;
        skipAdd++;
      } else {
        long p = transfer * point;
        long ops = ((val - p) + point - 1) / point;
        total += 2 * ops - 1;
        total += skipAdd;
        transfer = Math.max(ops - 1, 0);
        skipAdd = 0;
      }
    }

    return total <= m;
  }
}
