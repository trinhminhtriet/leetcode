import java.util.*;

class Solution {
    public int minimumPairRemoval(int[] nums) {
        int n = nums.length;
        long[] a = new long[n];
        for (int i = 0; i < n; i++)
            a[i] = nums[i];

        // maintain adjacent pairs {sum, index}
        TreeSet<Pair> s = new TreeSet<>(Comparator.comparingLong((Pair p) -> p.sum)
                .thenComparingInt(p -> p.index));

        // double-linked list
        int[] nxt = new int[n];
        int[] pre = new int[n];
        for (int i = 0; i < n; i++)
            nxt[i] = i + 1;
        for (int i = 0; i < n; i++)
            pre[i] = i - 1;

        // insert all pairs into set
        int cnt = 0;
        for (int i = 0; i < n - 1; i++) {
            if (a[i] > a[i + 1])
                cnt++;
            s.add(new Pair(a[i] + a[i + 1], i));
        }

        // simulate the process
        int ans = 0;
        while (cnt > 0) {
            Pair first = s.first();
            int i = first.index;
            int j = nxt[i];
            int p = pre[i];
            int q = nxt[j];

            // pair {i, j}
            if (a[i] > a[j])
                cnt--;
            if (p >= 0) {
                // pair {p, i}
                if (a[p] > a[i] && a[p] <= a[i] + a[j]) {
                    cnt--;
                } else if (a[p] <= a[i] && a[p] > a[i] + a[j]) {
                    cnt++;
                }
            }
            if (q < n) {
                // pair {j, q}
                if (a[q] >= a[j] && a[q] < a[i] + a[j]) {
                    cnt++;
                } else if (a[q] < a[j] && a[q] >= a[i] + a[j]) {
                    cnt--;
                }
            }

            // remove outdated pairs & add new pairs
            s.remove(first);
            if (p >= 0) {
                s.remove(new Pair(a[p] + a[i], p));
                s.add(new Pair(a[p] + a[i] + a[j], p));
            }
            if (q < n) {
                s.remove(new Pair(a[j] + a[q], j));
                s.add(new Pair(a[i] + a[j] + a[q], i));
                pre[q] = i;
            }
            nxt[i] = q;
            a[i] = a[i] + a[j];
            ans++;
        }

        return ans;
    }

    class Pair {
        long sum;
        int index;

        Pair(long sum, int index) {
            this.sum = sum;
            this.index = index;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Pair pair = (Pair) o;
            return sum == pair.sum && index == pair.index;
        }

        @Override
        public int hashCode() {
            return Objects.hash(sum, index);
        }
    }
}
