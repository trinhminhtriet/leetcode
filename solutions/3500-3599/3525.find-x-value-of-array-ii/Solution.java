class Solution {
    private class Node {
        int[] cnt;
        int prod;

        Node(int k) {
            cnt = new int[k];
            prod = 1;
        }
    }

    private class SegTree {
        int k, n, s;
        Node[] tree;

        SegTree(int[] nums, int k) {
            this.k = k;
            this.n = nums.length;
            this.s = 1;
            while (s < n) s <<= 1;
            tree = new Node[2 * s];
            for (int i = 0; i < 2 * s; i++) {
                tree[i] = new Node(k);
            }

            for (int i = 0; i < n; i++) {
                int a_mod = nums[i] % k;
                tree[s + i].cnt[a_mod] = 1;
                tree[s + i].prod = a_mod;
            }

            for (int p = s - 1; p > 0; p--) {
                tree[p] = merge(tree[2 * p], tree[2 * p + 1]);
            }
        }

        Node merge(Node l, Node r) {
            Node res = new Node(k);
            for (int i = 0; i < k; i++) res.cnt[i] = l.cnt[i];
            for (int r_b = 0; r_b < k; r_b++) {
                int c = r.cnt[r_b];
                if (c != 0) {
                    int r_ = (l.prod * r_b) % k;
                    res.cnt[r_] += c;
                }
            }
            res.prod = (l.prod * r.prod) % k;
            return res;
        }

        void update(int idx, int val) {
            int pos = s + idx;
            int a_mod = val % k;
            Arrays.fill(tree[pos].cnt, 0);
            tree[pos].cnt[a_mod] = 1;
            tree[pos].prod = a_mod;

            pos >>= 1;
            while (pos > 0) {
                tree[pos] = merge(tree[2 * pos], tree[2 * pos + 1]);
                pos >>= 1;
            }
        }

        Node query(int l, int r) {
            Node cnt_l = new Node(k);
            Node cnt_r = new Node(k);
            cnt_l.prod = cnt_r.prod = 1;
            l += s;
            r += s;
            while (l < r) {
                if ((l & 1) == 1) {
                    cnt_l = merge(cnt_l, tree[l++]);
                }
                if ((r & 1) == 1) {
                    cnt_r = merge(tree[--r], cnt_r);
                }
                l >>= 1;
                r >>= 1;
            }
            return merge(cnt_l, cnt_r);
        }
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        SegTree st = new SegTree(nums, k);
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int idx = queries[i][0];
            int val = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];
            st.update(idx, val);
            Node result = st.query(start, nums.length);
            res[i] = result.cnt[x];
        }
        return res;
    }
}
