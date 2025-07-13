class Solution {
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private int[] segmentTree;
    private int[] originalNums;
    private int n;
    private int maxC;

    private void build(int node, int strt, int end) {
        if (strt == end)
            segmentTree[node] = originalNums[strt];
        else {
            int mid = (strt + end) / 2;
            build(2 * node, strt, mid);
            build(2 * node + 1, mid + 1, end);
            segmentTree[node] = gcd(segmentTree[2 * node], segmentTree[2 * node + 1]);
        }
    }

    private int query(int node, int strt, int end, int l, int r) {
        if (r < strt || end < l)
            return 0;
        if (l <= strt && end <= r)
            return segmentTree[node];

        int mid = (strt + end) / 2;
        int p1 = query(2 * node, strt, mid, l, r);
        int p2 = query(2 * node + 1, mid + 1, end, l, r);

        if (p1 == 0)
            return p2;
        if (p2 == 0)
            return p1;
        return gcd(p1, p2);
    }

    private boolean check(int K) {
        int minLen = K + 1;
        int count = 0;
        int i = 0;

        if (minLen > n)
            return true;

        while (i <= n - minLen) {
            int currentHCF = query(1, 0, n - 1, i, i + minLen - 1);

            if (currentHCF >= 2) {
                count++;
                i = i + minLen;
            } else
                i++;
        }
        return count <= maxC;
    }

    public int minStable(int[] nums, int maxC) {
        this.originalNums = nums;
        this.n = nums.length;
        this.maxC = maxC;
        this.segmentTree = new int[4 * n];
        build(1, 0, n - 1);

        int left = 0;
        int right = n;
        int result = n;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (check(mid)) {
                result = mid;
                right = mid - 1;
            } else
                left = mid + 1;
        }
        return result;
    }
}
