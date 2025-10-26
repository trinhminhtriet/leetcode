class Solution {
    public long removeZeros(long n) {
        String x = "";
        String s = Long.toString(n);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '0') {
                x += s.charAt(i);
            }
        }
        return Long.parseLong(x);
    }
}