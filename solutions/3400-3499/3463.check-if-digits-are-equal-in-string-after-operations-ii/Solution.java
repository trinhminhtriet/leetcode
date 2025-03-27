import java.util.*;

class Solution {
  private static final Map<Integer, Integer> INV = Map.of(
      1, 1,
      3, 7,
      7, 3,
      9, 9);

  private static int[] TWOS = { 2, 4, 8, 6 };
  private static int[] FIVES = { 5 };

  private int[] mult(int[] a, int[] b) {
    return new int[] {
        a[0] + b[0],
        a[1] + b[1],
        (a[2] * b[2]) % 10
    };
  }

  private int[] div(int[] a, int[] b) {
    return new int[] {
        a[0] - b[0],
        a[1] - b[1],
        (a[2] * INV.getOrDefault(b[2], 1)) % 10
    };
  }

  private int red(List<int[]> it) {
    int sum = 0;
    for (int[] pair : it) {
      sum = (sum + (pair[0] * pair[1]) % 10) % 10;
    }
    return sum;
  }

  private int simplify(int[] tup) {
    int twos = tup[0], fives = tup[1], res = tup[2];

    if (twos > 0) {
      res = (res * TWOS[(twos - 1) % TWOS.length]) % 10;
    }
    if (fives > 0) {
      res = (res * FIVES[(fives - 1) % FIVES.length]) % 10;
    }

    return res;
  }

  public boolean hasSameDigits(String s) {
    int n = s.length() - 2;

    List<int[]> facts = new ArrayList<>();
    facts.add(new int[] { 0, 0, 1 });

    while (n >= facts.size()) {
      int num = facts.size();

      int twos = 0;
      while (num % 2 == 0) {
        num /= 2;
        twos++;
      }

      int fives = 0;
      while (num % 5 == 0) {
        num /= 5;
        fives++;
      }

      facts.add(mult(facts.get(facts.size() - 1), new int[] { twos, fives, num }));
    }

    List<Integer> pascal = new ArrayList<>();
    for (int k = 0; k <= n; k++) {
      pascal.add(simplify(div(div(facts.get(n), facts.get(k)), facts.get(n - k))));
    }

    List<int[]> aList = new ArrayList<>();
    List<int[]> bList = new ArrayList<>();
    for (int i = 0; i < s.length(); i++) {
      if (i < pascal.size()) {
        aList.add(new int[] { s.charAt(i) - '0', pascal.get(i) });
      }
      if (i > 0 && (i - 1) < pascal.size()) {
        bList.add(new int[] { s.charAt(i) - '0', pascal.get(i - 1) });
      }
    }

    int a = red(aList);
    int b = red(bList);

    return a == b;
  }
}
