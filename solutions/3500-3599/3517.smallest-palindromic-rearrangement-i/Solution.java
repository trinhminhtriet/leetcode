import java.util.Arrays;

class Solution {
    public String smallestPalindrome(String s) {
        int n = s.length();
        int m = n / 2;

        if (n == 1 || n == 2)
            return s;

        char[] fArr = s.substring(0, m).toCharArray();
        Arrays.sort(fArr);
        String f = new String(fArr);

        StringBuilder rev = new StringBuilder(f).reverse();

        if (n % 2 == 1) {
            f += s.charAt(m);
        }

        return f + rev.toString();
    }
}
