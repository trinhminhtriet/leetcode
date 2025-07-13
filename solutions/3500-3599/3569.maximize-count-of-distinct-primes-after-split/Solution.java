import java.util.*;

public class Solution {
    static final int MAXV = 100_001;

    private boolean[] isPrime = new boolean[MAXV];

    public Solution() {
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        for (int i = 2; i * i < MAXV; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < MAXV; j += i) {
                    isPrime[j] = false;
                }
            }
        }
    }

    class SegmentTree {
        int[] sum, max;
        int size;

        SegmentTree(int n) {
            size = 1;
            while (size < n + 2)
                size <<= 1;
            sum = new int[2 * size];
            max = new int[2 * size];
        }

        void pointAdd(int pos, int delta) {
            int i = pos + size;
            sum[i] += delta;
            max[i] = Math.max(sum[i], 0);
            for (i >>= 1; i > 0; i >>= 1) {
                sum[i] = sum[2 * i] + sum[2 * i + 1];
                max[i] = Math.max(max[2 * i], sum[2 * i] + max[2 * i + 1]);
            }
        }

        void applyRange(int from, int to, int delta) {
            if (from + 1 <= to) {
                pointAdd(from + 1, delta);
                pointAdd(to + 1, -delta);
            }
        }

        int getMax() {
            return max[1];
        }
    }

    public int[] maximumCount(int[] nums, int[][] queries) {
        int n = nums.length;

        SegmentTree segTree = new SegmentTree(n);
        Map<Integer, TreeSet<Integer>> primeIndices = new HashMap<>();
        int distinct = 0;

        // Initialize
        for (int i = 0; i < n; i++) {
            int v = nums[i];
            if (isPrime[v]) {
                primeIndices.computeIfAbsent(v, x -> new TreeSet<>()).add(i);
            }
        }

        for (int p : primeIndices.keySet()) {
            TreeSet<Integer> set = primeIndices.get(p);
            int first = set.first(), last = set.last();
            segTree.applyRange(first, last, +1);
            distinct++;
        }

        int[] result = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            int idx = queries[q][0], val = queries[q][1];
            int old = nums[idx];

            if (old == val) {
                result[q] = distinct + segTree.getMax();
                continue;
            }

            // Remove old value
            if (isPrime[old]) {
                TreeSet<Integer> set = primeIndices.get(old);
                int f1 = set.first(), l1 = set.last();
                segTree.applyRange(f1, l1, -1);
                set.remove(idx);
                if (set.isEmpty()) {
                    primeIndices.remove(old);
                    distinct--;
                } else {
                    int f2 = set.first(), l2 = set.last();
                    segTree.applyRange(f2, l2, +1);
                }
            }

            // Add new value
            if (isPrime[val]) {
                if (!primeIndices.containsKey(val)) {
                    TreeSet<Integer> set = new TreeSet<>();
                    set.add(idx);
                    primeIndices.put(val, set);
                    segTree.applyRange(idx, idx, +1);
                    distinct++;
                } else {
                    TreeSet<Integer> set = primeIndices.get(val);
                    int f1 = set.first(), l1 = set.last();
                    segTree.applyRange(f1, l1, -1);
                    set.add(idx);
                    int f2 = set.first(), l2 = set.last();
                    segTree.applyRange(f2, l2, +1);
                }
            }

            nums[idx] = val;
            result[q] = distinct + segTree.getMax();
        }

        return result;
    }
}
