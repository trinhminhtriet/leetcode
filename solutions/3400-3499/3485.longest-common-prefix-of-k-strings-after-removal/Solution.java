class Solution {
  static final long BASE = 131L;

  public int[] longestCommonPrefix(String[] words, int k) {
    int n = words.length;
    int[] ans = new int[n];
    int mxLen = 0;
    for (String word : words) {
      mxLen = Math.max(mxLen, word.length());
    }

    Map<Long, Integer>[] freq = new Map[mxLen + 1];
    for (int i = 0; i <= mxLen; i++)
      freq[i] = new HashMap<>();
    for (String word : words) {
      long hash = 0;
      for (int i = 0; i < word.length(); i++) {
        hash = hash * BASE + (word.charAt(i) - 'a' + 1);
        int d = i + 1;
        freq[d].put(hash, freq[d].getOrDefault(hash, 0) + 1);
      }
    }
    int[] bestFreq = new int[mxLen + 1];
    int[] secFreq = new int[mxLen + 1];
    long[] bestHash = new long[mxLen + 1];
    for (int d = 1; d <= mxLen; d++) {
      for (Map.Entry<Long, Integer> entry : freq[d].entrySet()) {
        int f = entry.getValue();
        long h = entry.getKey();
        if (f > bestFreq[d]) {
          secFreq[d] = bestFreq[d];
          bestFreq[d] = f;
          bestHash[d] = h;
        } else if (f > secFreq[d]) {
          secFreq[d] = f;
        }
      }
    }
    long[][] prefixHashes = new long[n][];
    for (int i = 0; i < n; i++) {
      String word = words[i];
      int len = word.length();
      prefixHashes[i] = new long[len + 1];
      for (int j = 0; j < len; j++) {
        prefixHashes[i][j + 1] = prefixHashes[i][j] * BASE + (word.charAt(j) - 'a' + 1);
      }
    }
    for (int i = 0; i < n; i++) {
      String word = words[i];
      int len = word.length();
      int lo = 0, hi = mxLen + 1;
      while (lo + 1 < hi) {
        int mid = (lo + hi) / 2;
        int cur;
        if (mid <= len && prefixHashes[i][mid] == bestHash[mid])
          cur = Math.max(bestFreq[mid] - 1, secFreq[mid]);
        else
          cur = bestFreq[mid];
        if (cur < k)
          hi = mid;
        else
          lo = mid;
      }
      ans[i] = lo;
    }
    return ans;
  }
}