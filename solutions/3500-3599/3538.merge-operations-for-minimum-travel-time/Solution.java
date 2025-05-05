public class Solution {
    public int minTravelTime(int l, int n, int k, int[] position, int[] time) {
        long[] pref_t = new long[n];
        pref_t[0] = time[0];
        for (int i = 1; i < n; i++) {
            pref_t[i] = pref_t[i - 1] + time[i];
        }

        List<Map<Integer, Long>>[] dp = new List[n];
        for (int i = 0; i < n; i++) {
            dp[i] = new ArrayList<>();
            for (int m = 0; m <= k; m++) {
                dp[i].add(new HashMap<>());
            }
        }
        dp[0].get(0).put(time[0], 0L);

        for (int i = 0; i < n; i++) {
            for (int m = 0; m <= k; m++) {
                for (Map.Entry<Integer, Long> e : dp[i].get(m).entrySet()) {
                    int accum = e.getKey();
                    long time_t = e.getValue();
                    for (int j = i + 1; j < n; j++) {
                        int rem = j - i - 1;
                        if (m + rem > k) continue;
                        long seg_time = (long)(position[j] - position[i]) * accum;
                        long new_time = time_t + seg_time;
                        int new_acc = (int)(pref_t[j] - pref_t[i]);
                        int rd = m + rem;
                        Long curr = dp[j].get(rd).get(new_acc);
                        if (curr == null || new_time < curr) {
                            dp[j].get(rd).put(new_acc, new_time);
                        }
                    }
                }
            }
        }

        long res = Long.MAX_VALUE;
        for (long time_t : dp[n - 1].get(k).values()) {
            res = Math.min(res, time_t);
        }
        return (int)res;
    }
}

