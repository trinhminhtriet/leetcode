class Solution {
    public String lexSmallest(String s) {
        int n = s.length();
        String ans = s;
        for(int i = 0; i < n; i++) {
            String s1 = fun1(s, i);
            String s2 = fun2(s, n - i);
            if(s1.compareTo(s2) < 0 && s1.compareTo(ans) < 0) ans = s1; 
            if(s2.compareTo(s1) < 0 && s2.compareTo(ans) < 0) ans = s2; 
        }
        return ans;
    }
    String fun1(String s, int idx) {
        StringBuilder sb = new StringBuilder();
        for(int i = idx; i >= 0; i--) sb.append(s.charAt(i));
        for(int i = idx + 1; i < s.length(); i++) sb.append(s.charAt(i));
        return sb.toString();
    }
    String fun2(String s, int idx) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < idx; i++) sb.append(s.charAt(i));
        for(int i = s.length() - 1; i >= idx; i--) sb.append(s.charAt(i));
        return sb.toString();
    }
}