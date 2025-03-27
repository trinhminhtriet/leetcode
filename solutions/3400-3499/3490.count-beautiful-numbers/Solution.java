class Solution {
  static public int beautifulNumbers(int l, int r) {
    HashMap<String, Long> map = new HashMap<>();
    long ans1 = solve(0, 1, 0, 0, 0, 0, 0, 0, 0, Integer.toString(r).toCharArray(), map);
    map.clear();
    long ans2 = solve(0, 1, 0, 0, 0, 0, 0, 0, 0, Integer.toString(l - 1).toCharArray(), map);
    return (int) (ans1 - ans2);
  }

  static long canBe(int two, int three, int five, int seven, long sum) {
    long ans = 1L;
    for (int i = 1; i <= two; i++)
      ans *= 2L;
    for (int i = 1; i <= three; i++)
      ans *= 3L;
    for (int i = 1; i <= five; i++)
      ans *= 5L;
    for (int i = 1; i <= seven; i++)
      ans *= 7L;
    return ans % sum == 0 ? 1 : 0;
  }

  static int num = 0;

  static long solve(int i, int tight, int sum, int two, int three, int five, int seven, int zer0,
      int hasAnyDigitAppeared, char[] arr, HashMap<String, Long> map) {
    if (i == arr.length) {
      num++;
      if (zer0 == 1 || sum == 0)
        return 1;
      return canBe(two, three, five, seven, sum);
    }

    String str = i + " - " + tight + " - " + sum + " - " + two + " - " + three + " - " + five + " - " + seven
        + " - " + zer0;

    Long res = map.get(str);
    if (res != null) {
      return res;
    }
    int limit = (tight == 1) ? arr[i] - '0' : 9;
    long ans = 0;
    for (int j = 0; j <= limit; j++) {
      int updatedSum = sum + j;
      int isStillTight = (tight & (j == (arr[i] - '0') ? 1 : 0));

      if (j == 0) {
        ans += solve(i + 1, isStillTight, updatedSum, two, three, five, seven, (hasAnyDigitAppeared & 1),
            hasAnyDigitAppeared, arr, map);
      } else if (j == 1) {
        ans += solve(i + 1, isStillTight, updatedSum, two, three, five, seven, zer0, 1, arr, map);
      } else if (j == 2) {
        ans += solve(i + 1, isStillTight, updatedSum, two + 1, three, five, seven, zer0, 1, arr, map);
      } else if (j == 3) {
        ans += solve(i + 1, isStillTight, updatedSum, two, three + 1, five, seven, zer0, 1, arr, map);
      } else if (j == 4) {
        ans += solve(i + 1, isStillTight, updatedSum, two + 2, three, five, seven, zer0, 1, arr, map);
      } else if (j == 5) {
        ans += solve(i + 1, isStillTight, updatedSum, two, three, five + 1, seven, zer0, 1, arr, map);
      } else if (j == 6) {
        ans += solve(i + 1, isStillTight, updatedSum, two + 1, three + 1, five, seven, zer0, 1, arr, map);
      } else if (j == 7) {
        ans += solve(i + 1, isStillTight, updatedSum, two, three, five, seven + 1, zer0, 1, arr, map);
      } else if (j == 8) {
        ans += solve(i + 1, isStillTight, updatedSum, two + 3, three, five, seven, zer0, 1, arr, map);
      } else {
        ans += solve(i + 1, isStillTight, updatedSum, two, three + 2, five, seven, zer0, 1, arr, map);
      }
    }
    map.put(str, ans);
    return ans;
  }
}