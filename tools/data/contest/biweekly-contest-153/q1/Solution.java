public class Solution {
  public static int reverseDegree(String s) {
      int sum = 0;
      for (int i = 0; i < s.length(); i++) {
          int reverseIndex = 26 - (s.charAt(i) - 'a'); // Reverse alphabet index
          sum += reverseIndex * (i + 1); // Multiply by 1-based position
      }
      return sum;
  }
}
