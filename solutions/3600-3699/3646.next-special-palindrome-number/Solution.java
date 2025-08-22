class Solution {
    static List<Long> L1 = new ArrayList<>();
    static boolean initialized = false;

    private static void findSubsets(List<Integer> arr, int index, int target, List<Integer> curr, List<List<Integer>> result, int max_odds, int odd_count) {
        if (index >= arr.size()) {
            if (target == 0) {
                result.add(new ArrayList<>(curr));
            }
            return;
        }

        if (odd_count + (arr.get(index) % 2) <= max_odds) {
            curr.add(arr.get(index));
            findSubsets(arr, index + 1, target - arr.get(index), curr, result, max_odds, odd_count + (arr.get(index) % 2));
            curr.remove(curr.size() - 1);
        }

        findSubsets(arr, index + 1, target, curr, result, max_odds, odd_count);
    }

    private static List<List<Integer>> perfectSum(List<Integer> arr, int target, int max_odds) {
        List<List<Integer>> result = new ArrayList<>();
        findSubsets(arr, 0, target, new ArrayList<>(), result, max_odds, 0);
        return result;
    }

    private static void mainLogicInit() {
        List<Integer> arr1 = Arrays.asList(2, 4, 6, 8);
        List<Integer> arr2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Set<String> L = new HashSet<>();

        for (int i = 2; i < 17; i++) {
            List<List<Integer>> res;
            if (i % 2 == 0) {
                res = perfectSum(arr1, i, arr1.size());
            } else {
                res = perfectSum(arr2, i, 1);
            }

            boolean x = false;
            int t = 0;
            for (List<Integer> arr : res) {
                StringBuilder sb = new StringBuilder();
                x = false;
                for (int val : arr) {
                    if (val % 2 == 1) {
                        x = true;
                        t = val;
                    }
                    sb.append(String.valueOf(val).repeat(val / 2));
                }

                String s = sb.toString();
                char[] chars = s.toCharArray();
                Arrays.sort(chars);

                do {
                    String sortedStr = new String(chars);
                    if (x) {
                        L.add(sortedStr + t + new StringBuilder(sortedStr).reverse());
                    } else {
                        L.add(sortedStr + new StringBuilder(sortedStr).reverse());
                    }
                } while (nextPermutation(chars));
            }
        }

        for (String str : L) {
            L1.add(Long.parseLong(str));
        }
        Collections.sort(L1);
    }

    private static boolean nextPermutation(char[] arr) {
        int i = arr.length - 2;
        while (i >= 0 && arr[i] >= arr[i + 1]) i--;
        if (i < 0) return false;

        int j = arr.length - 1;
        while (arr[j] <= arr[i]) j--;
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

        for (int l = i + 1, r = arr.length - 1; l < r; l++, r--) {
            char t = arr[l];
            arr[l] = arr[r];
            arr[r] = t;
        }
        return true;
    }

    public long specialPalindrome(long n) {
        if (!initialized) {
            mainLogicInit();
            initialized = true;
        }

        if (n == 0) {
            return 1;
        }

        int x = Collections.binarySearch(L1, n);
        if (x < 0) x = -x - 1; // upper_bound
        else x += 1; // if exact match, move to next
        return L1.get(x);
    }
}
