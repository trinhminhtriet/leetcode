class Solution {
    public int longestBalanced(String s) {
        int n = s.length();
        int len = 1; int max = 1;
        for(int i=1; i<n; i++){
            if(s.charAt(i) == s.charAt(i-1)){
                len++;
                max = Math.max(max, len);
            } else {
                len = 1;
            }
        }
        int a = 0, b = 0, c = 0;
        Map<String, Integer> map = new HashMap<>();
        map.put("0,0", -1);
        for(int i=0; i<n; i++){
            char ch = s.charAt(i);
            if(ch == 'a'){
                a++;
            } else if(ch == 'b'){
                b++;
            } else {
                c++;
            }
            int diff1 = a - b; int diff2 = a - c;
            String key = diff1 + "," + diff2;
            if(map.containsKey(key)){
                max = Math.max(max, i-map.get(key));
            } else {
                map.put(key, i);
            } 
        }
        max = Math.max(max, solve(s, 'a', 'b', 'c'));
        max = Math.max(max, solve(s, 'a', 'c', 'b'));
        max = Math.max(max, solve(s, 'b', 'c', 'a'));

        return max;
    }
    public int solve(String s, char c1, char c2, char skip){
        int maxLen = 0; int n = s.length();
        for(int i=0; i<s.length(); i++){
            while(i < n && s.charAt(i) == skip) i++;
            if(i >= n) break;
            int p = i;
            while(i < n && s.charAt(i) != skip) i++;
            int q = i-1; int x = 0; int y = 0;
            Map<Integer, Integer> map = new HashMap<>();
            map.put(0, p-1);
            for(int j=p; j<=q; j++){
                char ch = s.charAt(j);
                if (ch == c1){
                    x++;
                } else if(ch == c2){
                    y++;
                }
                int key = x-y;
                if(map.containsKey(key)){
                    maxLen = Math.max(maxLen, j-map.get(key));
                } else {
                    map.put(key, j);
                }
            }
        }
        return maxLen;
    }
}