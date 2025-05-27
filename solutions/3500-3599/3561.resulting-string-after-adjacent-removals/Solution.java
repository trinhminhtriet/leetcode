class Solution {
    public String resultingString(String s) {
        StringBuilder sb = new StringBuilder();

        for (char ch : s.toCharArray()) {
            if (sb.length() > 0) {
                char top = sb.charAt(sb.length() - 1);
                int diff = Math.abs(top - ch);

                if (diff == 1 || diff == 25) {
                    sb.deleteCharAt(sb.length() - 1);
                    continue;
                }
            }
            sb.append(ch);
        }

        return sb.toString();
    }
}
