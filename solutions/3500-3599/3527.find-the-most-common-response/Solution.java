import java.util.*;

class Solution {
    public String findCommonResponse(List<List<String>> responses) {
        int n = responses.size();
        Map<String, BitSet> map = new HashMap<>(n);
        
        for (int i = 0; i < n; i++) {
            List<String> response = responses.get(i);
            for (String word : response) {
                map.putIfAbsent(word, new BitSet(1000));
                map.get(word).set(i);
            }
        }
        
        String answer = "";
        int maxCount = 0;
        
        for (Map.Entry<String, BitSet> entry : map.entrySet()) {
            String word = entry.getKey();
            int count = entry.getValue().cardinality();
            
            if (count > maxCount || (count == maxCount && word.compareTo(answer) < 0)) {
                answer = word;
                maxCount = count;
            }
        }
        
        return answer;
    }
}

