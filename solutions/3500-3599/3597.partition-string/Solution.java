class Solution {
    public List<String> partitionString(String s) {
        Map<String, Integer> seen = new HashMap<>();
        List<String> result = new ArrayList<>();

        String first = String.valueOf(s.charAt(0));
        result.add(first);
        seen.put(first, 1);

        int i = 1;
        String current = "";

        while (i < s.length()) {
            current += s.charAt(i);

            if (!seen.containsKey(current)) {
                result.add(current);
                seen.put(current, 1);
                current = "";
            }

            i++;
        }

        return result;
    }
}
