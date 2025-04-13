class Solution {
    public static int uniqueXorTriplets(int[] arr) {
        int n = arr.length;
        boolean[] freq = new boolean[2048];
        int len = 0, idx = 0, ans = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (!freq[arr[i] ^ arr[j]])
                    len++;
                freq[arr[i] ^ arr[j]] = true;
            }
        }

        int[] nums = new int[len];

        for (int i = 0; i < 2048; i++)
            if (freq[i])
                nums[idx++] = i;

        Arrays.fill(freq, false);

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < n; j++)
                freq[nums[i] ^ arr[j]] = true;
        }

        for (boolean b : freq)
            if (b)
                ans++;

        return ans;
    }

}
