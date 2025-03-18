class Solution {
    public List<Integer> transformArray(int[] arr) {
        boolean f = true;
        while (f) {
            f = false;
            int[] t = arr.clone();
            for (int i = 1; i < t.length - 1; ++i) {
                if (t[i] > t[i - 1] && t[i] > t[i + 1]) {
                    --arr[i];
                    f = true;
                }
                if (t[i] < t[i - 1] && t[i] < t[i + 1]) {
                    ++arr[i];
                    f = true;
                }
            }
        }
        List<Integer> ans = new ArrayList<>();
        for (int x : arr) {
            ans.add(x);
        }
        return ans;
    }
}