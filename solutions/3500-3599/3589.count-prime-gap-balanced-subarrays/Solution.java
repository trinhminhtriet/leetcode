import java.util.*;

class Solution {
    private static final int MX = 50005;

    public boolean[] sieveOfEratosthenes(int n) {
        boolean[] prime = new boolean[n + 1];
        Arrays.fill(prime, true);
        prime[0] = false;
        prime[1] = false;

        for (int p = 2; p * p <= n; p++) {
            if (prime[p]) {
                for (int i = p * p; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }

        return prime;
    }

    public int primeSubarray(int[] nums, int k) {
        int n = nums.length;
        boolean[] isPrime = sieveOfEratosthenes(MX);

        int[] prefSum = new int[n];
        prefSum[0] = isPrime[nums[0]] ? 1 : 0;
        for (int i = 1; i < n; i++) {
            prefSum[i] = prefSum[i - 1] + (isPrime[nums[i]] ? 1 : 0);
        }

        int[] startFrom = new int[n];
        Arrays.fill(startFrom, -1);
        for (int i = 0; i < n; i++) {
            int start = i, end = n - 1, idx = -1;
            while (start <= end) {
                int mid = start + (end - start) / 2;
                int total = prefSum[mid] - (i > 0 ? prefSum[i - 1] : 0);
                if (total >= 2) {
                    idx = mid;
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
            startFrom[i] = idx;
        }

        int ans = 0;
        TreeMap<Integer, Integer> primeCount = new TreeMap<>();
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (startFrom[i] == -1)
                break;

            while (j < n) {
                if (isPrime[nums[j]]) {
                    primeCount.put(nums[j], primeCount.getOrDefault(nums[j], 0) + 1);
                }

                if (primeCount.size() >= 2) {
                    int min = primeCount.firstKey();
                    int max = primeCount.lastKey();
                    if (max - min > k) {
                        if (isPrime[nums[j]]) {
                            int count = primeCount.get(nums[j]);
                            if (count == 1) {
                                primeCount.remove(nums[j]);
                            } else {
                                primeCount.put(nums[j], count - 1);
                            }
                        }
                        break;
                    }
                }

                j++;
            }

            if (j > startFrom[i]) {
                ans += (j - startFrom[i]);
            }

            if (isPrime[nums[i]]) {
                int count = primeCount.get(nums[i]);
                if (count == 1) {
                    primeCount.remove(nums[i]);
                } else {
                    primeCount.put(nums[i], count - 1);
                }
            }
        }

        return ans;
    }
}
