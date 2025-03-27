class Solution {
  public long minTime(int[] skill, int[] mana) {
    int n = skill.length;
    int m = mana.length;
    long[] done = new long[n + 1];

    for (int j = 0; j < m; ++j) {
      for (int i = 0; i < n; ++i) {
        done[i + 1] = Math.max(done[i + 1], done[i]) + (long) mana[j] * skill[i];
      }
      for (int i = n - 1; i > 0; --i) {
        done[i] = done[i + 1] - (long) mana[j] * skill[i];
      }
    }
    return done[n]; // Using array index instead of .back()
  }
}