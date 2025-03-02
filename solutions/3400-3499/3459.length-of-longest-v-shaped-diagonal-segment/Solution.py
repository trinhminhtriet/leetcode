class Solution:
    def lenOfVDiagonal(self, g: List[List[int]]) -> int:
        @cache
        def dp(i,j,x,d,k):
            if not (0 <= i < n and 0 <= j < m): return 0
            if g[i][j] != x: return 0
            res = dp(i + ds[d][0], j + ds[d][1], nx[x], d, k) + 1
            if k > 0:
                d2 = (d + 1) % 4
                res2 = dp(i + ds[d2][0], j + ds[d2][1], nx[x], d2, 0) + 1
                res = max(res, res2)
            return res

        ds = [[1,1],[1,-1],[-1,-1],[-1,1]]
        nx = [2,2,0]
        res = 0
        n, m = len(g), len(g[0])
        for i in range(n):
            for j in range(m):
                if g[i][j] == 1:
                    cur = max(dp(i, j, 1, d, 1) for d in range(4))
                    res = max(res, cur)
        return res