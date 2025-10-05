class Solution {
    public String removeSubstring(String s, int k) {
        StringBuilder ans = new StringBuilder();
        for (char ch : s.toCharArray()) {
            ans.append(ch);
            int size = ans.length();
            if (size >= 2 * k) {
                boolean valid = true;
                for (int i = 0; i < k; i++) {
                    if (ans.charAt(size - 2 * k + i) != '(') {
                        valid = false;
                        break;
                    }
                    if (ans.charAt(size - k + i) != ')') {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    ans.setLength(size - 2 * k); // shrink the string
                }
            }
        }
        return ans.toString();
    }
}
