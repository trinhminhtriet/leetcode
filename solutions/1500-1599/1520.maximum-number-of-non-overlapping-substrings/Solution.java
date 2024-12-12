class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int n = s.length();
        int[] left = new int[26], right = new int[26];
        Arrays.fill(left, n);
        // Record the leftmost and rightmost index for each character.
        for (int i = 0; i < n; ++i) {
            left[s.charAt(i) - 'a'] = Math.min(left[s.charAt(i) - 'a'], i);
            right[s.charAt(i) - 'a'] = i;
        }
        List<String> res = new ArrayList<>();
        int l = -1, r = -1;
        // For each character (if it's the leftmost occurrence),
        // check if it forms a valid solution.
        for (int i = 0; i < n; ++i) {
            if (i != left[s.charAt(i) - 'a']) continue;
            int newR = right[s.charAt(i) - 'a'];
            for (int j = i + 1; j <= newR; ++j) {
                if (left[s.charAt(j) - 'a'] < i) {
                    newR = n;
                    break;
                }
                newR = Math.max(newR, right[s.charAt(j) - 'a']);
            }
            if (newR < n && (i > r || newR < right[s.charAt(r) - 'a'])) {
                if (i > r) res.add(s.substring(i, newR + 1));
                else res.set(res.size() - 1, s.substring(i, newR + 1));
                l = i;
                r = newR;
            }
        }
        return res;
    }
}