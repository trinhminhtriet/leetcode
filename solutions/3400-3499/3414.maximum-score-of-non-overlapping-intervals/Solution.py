class Solution:
    def maximumWeight(self, A: List[List[int]]) -> List[int]:
        seen = {}
        for i, (l,r,w) in enumerate(A):
            if (l,r,w) not in seen:
                seen[l,r,w] = i
        A = sorted(seen)

        @cache
        def dp(i, k):
            if k == 0: return [0, []]
            if i == len(A): return [0, []]
            skip = dp(i + 1, k)
            j = bisect_right(A, (A[i][1] + 1,))
            pick0 = dp(j, k - 1)
            pick = [pick0[0] - A[i][2], pick0[1][:]]
            insort(pick[1], seen[A[i]])
            return min(skip, pick)

        return list(dp(0, 4)[1])