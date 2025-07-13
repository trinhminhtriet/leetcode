class Solution {
    private class Pair {
        private int first;
        private int second;

        private Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    private class trie {
        trie[] bits = new trie[2];
        int cnt;
        int size;

        private trie(int size) {
            this.bits[0] = this.bits[1] = null;
            this.cnt = 0;
            this.size = size;
        }

        private boolean alreadyExist(int num) {
            trie temp = this;
            for (int i = this.size; i >= 0; i--) {
                int bit = (num & (1 << i)) != 0 ? 1 : 0;
                if (temp.bits[bit] == null)
                    return false;
                temp = temp.bits[bit];
            }
            return true;
        }

        private void insert(int num) {
            if (alreadyExist(num))
                return;
            trie temp = this;
            for (int i = this.size; i >= 0; i--) {
                int bit = (num & (1 << i)) != 0 ? 1 : 0;
                if (temp.bits[bit] == null)
                    temp.bits[bit] = new trie(17);
                temp.cnt++;
                temp = temp.bits[bit];
            }
        }

        private trie mergeTries(trie parent, trie child) {
            if (parent == null)
                return child;
            if (child == null)
                return parent;
            for (int bit = 0; bit <= 1; bit++) {
                trie temp_1 = parent.bits[bit];
                trie temp_2 = child.bits[bit];
                if (temp_1 == null)
                    parent.bits[bit] = temp_2;
                else
                    parent.bits[bit] = mergeTries(parent.bits[bit], child.bits[bit]);
            }
            parent.cnt = 0;
            if (parent.bits[0] != null)
                parent.cnt += parent.bits[0].cnt == 0 ? 1 : parent.bits[0].cnt;
            if (parent.bits[1] != null)
                parent.cnt += parent.bits[1].cnt == 0 ? 1 : parent.bits[1].cnt;
            return parent;
        }

        private void merge(trie child) {
            mergeTries(this, child);
        }

        private int find(int k) {
            if (this.cnt < k)
                return -1;
            trie temp = this;
            int res = 0;
            for (int i = this.size; i >= 0; i--) {
                if (temp.bits[0] != null && (temp.bits[0].cnt >= k || k == 1)) {
                    temp = temp.bits[0];
                } else {
                    k -= temp.bits[0] == null ? 0 : temp.bits[0].cnt;
                    res |= (1 << i);
                    temp = temp.bits[1];
                }
            }
            return res;
        }
    }

    private trie dfs(int[] ans, int node, List<List<Pair>> q, List<List<Integer>> mp, int[] vals) {
        trie parent = new trie(17);
        for (int child : mp.get(node)) {
            vals[child] ^= vals[node];
            trie chd = dfs(ans, child, q, mp, vals);
            parent.merge(chd);
        }
        parent.insert(vals[node]);
        for (Pair it : q.get(node)) {
            ans[it.second] = parent.find(it.first);
        }
        return parent;
    }

    public int[] kthSmallest(int[] par, int[] vals, int[][] queries) {
        int n = vals.length;
        int m = queries.length;
        List<List<Integer>> mp = new ArrayList<>();
        List<List<Pair>> q = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            mp.add(new ArrayList<>());
            q.add(new ArrayList<>());
        }
        for (int i = 1; i < n; i++)
            mp.get(par[i]).add(i);
        for (int i = 0; i < m; i++)
            q.get(queries[i][0]).add(new Pair(queries[i][1], i));
        int[] ans = new int[m];
        dfs(ans, 0, q, mp, vals);
        return ans;
    }
}
