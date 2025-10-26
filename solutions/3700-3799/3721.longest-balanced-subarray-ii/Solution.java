class SegmentTree {
    int n;
    int[] minTree, maxTree, lazy;

    public SegmentTree(int n) {
        this.n = n;
        minTree = new int[4 * n];
        maxTree = new int[4 * n];
        lazy = new int[4 * n];
    }

    private void push(int node, int start, int end) {
        if (lazy[node] != 0) {
            minTree[node] += lazy[node];
            maxTree[node] += lazy[node];
            if (start != end) {
                lazy[2 * node] += lazy[node];
                lazy[2 * node + 1] += lazy[node];
            }
            lazy[node] = 0;
        }
    }

    public void updateRange(int node, int start, int end, int l, int r, int val) {
        push(node, start, end);
        if (start > end || start > r || end < l) {
            return;
        }
        if (l <= start && end <= r) {
            lazy[node] += val;
            push(node, start, end);
            return;
        }
        int mid = (start + end) / 2;
        updateRange(2 * node, start, mid, l, r, val);
        updateRange(2 * node + 1, mid + 1, end, l, r, val);
        minTree[node] = Math.min(minTree[2 * node], minTree[2 * node + 1]);
        maxTree[node] = Math.max(maxTree[2 * node], maxTree[2 * node + 1]);
    }

    public int findLeftmostZero(int node, int start, int end) {
        push(node, start, end);
        if (minTree[node] > 0 || maxTree[node] < 0) {
            return -1;
        }
        if (start == end) {
            return minTree[node] == 0 ? start : -1;
        }
        int mid = (start + end) / 2;
        int left = findLeftmostZero(2 * node, start, mid);
        if (left != -1) return left;
        return findLeftmostZero(2 * node + 1, mid + 1, end);
    }
}

public class Solution {
    public int longestBalanced(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> prev = new HashMap<>();

        SegmentTree st = new SegmentTree(n);
        int res = 0;

        for (int r = 0; r < n; r++) {
            int v = nums[r];
            int val = (v % 2 == 0) ? 1 : -1;

            if (prev.containsKey(v)) {
                st.updateRange(1, 0, n - 1, 0, prev.get(v), -val);
            }

            st.updateRange(1, 0, n - 1, 0, r, val);
            prev.put(v, r);

            int l = st.findLeftmostZero(1, 0, n - 1);
            if (l != -1 && l <= r) {
                res = Math.max(res, r - l + 1);
            }
        }

        return res;
    }
}