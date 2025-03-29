class Solution {
  public int maxActiveSectionsAfterTrade(String s) {
      int n = s.length();
      int totalOnes = 0;
      for (int i = 0; i < n; i++) {
          if (s.charAt(i) == '1') {
              totalOnes++;
          }
      }

      String augmented = '1' + s + '1';
      int augmentedLen = augmented.length();

      int[] zeroBlockStart = new int[augmentedLen];
      int[] zeroBlockEnd = new int[augmentedLen];

      for (int i = 0; i < augmentedLen; i++) {
          if (augmented.charAt(i) == '0') {
              if (i > 0 && augmented.charAt(i - 1) == '0') {
                  zeroBlockStart[i] = zeroBlockStart[i - 1];
              } else {
                  zeroBlockStart[i] = i;
              }
          }
      }

      for (int i = augmentedLen - 1; i >= 0; i--) {
          if (augmented.charAt(i) == '0') {
              if (i < augmentedLen - 1 && augmented.charAt(i + 1) == '0') {
                  zeroBlockEnd[i] = zeroBlockEnd[i + 1];
              } else {
                  zeroBlockEnd[i] = i;
              }
          }
      }

      List<int[]> eligibleOneSegments = new ArrayList<>();
      for (int i = 0; i < augmentedLen; ) {
          if (augmented.charAt(i) == '1') {
              int start = i;
              while (i < augmentedLen && augmented.charAt(i) == '1') {
                  i++;
              }
              int end = i - 1;
              // Check if surrounded by 0s
              boolean leftZero = start > 0 && augmented.charAt(start - 1) == '0';
              boolean rightZero = end < augmentedLen - 1 && augmented.charAt(end + 1) == '0';
              if (leftZero && rightZero) {
                  eligibleOneSegments.add(new int[]{start, end});
              }
          } else {
              i++;
          }
      }

      int maxGain = 0;
      for (int[] segment : eligibleOneSegments) {
          int l = segment[0];
          int r = segment[1];
          int oneSize = r - l + 1;

          // Left zero segment is at l-1
          int leftZeroStart = zeroBlockStart[l - 1];
          int leftZeroEnd = zeroBlockEnd[l - 1];
          int leftZeroSize = leftZeroEnd - leftZeroStart + 1;

          // Right zero segment is at r+1
          int rightZeroStart = zeroBlockStart[r + 1];
          int rightZeroEnd = zeroBlockEnd[r + 1];
          int rightZeroSize = rightZeroEnd - rightZeroStart + 1;

          // New zero segment is from leftZeroStart to rightZeroEnd
          int newZeroSize = leftZeroSize + rightZeroSize + oneSize;

          // Check if the new zero segment is surrounded by 1s
          boolean leftOne = leftZeroStart > 0 && augmented.charAt(leftZeroStart - 1) == '1';
          boolean rightOne = rightZeroEnd < augmentedLen - 1 && augmented.charAt(rightZeroEnd + 1) == '1';
          if (leftOne && rightOne) {
              int gain = newZeroSize - oneSize;
              if (gain > maxGain) {
                  maxGain = gain;
              }
          }
      }

      return totalOnes + maxGain;
  }
}
