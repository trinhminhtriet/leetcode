class Solution {
    public int maxSubstrings(String word) {
        int len = 0;
        int[] arr = new int[26];
        Arrays.fill(arr, -1); // initialize array with -1

        int n = word.length();
        for (int i = 0; i < n; i++) {
            int idx = word.charAt(i) - 'a';
            if (arr[idx] != -1 && i - arr[idx] + 1 >= 4) {
                len++;
                Arrays.fill(arr, -1); // reset array to -1
            } else {
                if (arr[idx] == -1) {
                    arr[idx] = i;
                }
            }
        }

        return len;
    }
}
