class Solution {
    public boolean isRationalEqual(String S, String T) {
        return Math.abs(value(S) - value(T)) < 1E-9;
    }

    public double value(String S) {
        int i = S.indexOf("(");
        if (i > 0) {
            String base = S.substring(0, i);
            String rep = S.substring(i + 1, S.length() - 1);
            for (int j = 0; j < 20; ++j) base += rep;
            return Double.valueOf(base);
        } else {
            return Double.valueOf(S);
        }
    }
}