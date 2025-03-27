class Solution:
    def maxSubarrays(self, N: int, A: List[List[int]]) -> int:
        right = [[] for _ in range(N + 1)]
        for a, b in A:
            right[max(a, b)].append(min(a, b))

        ans = 0
        left = [0, 0]
        imp = [0] * (N + 1)
        for r in range(1, N + 1):
            for l in right[r]:
                left = max(left, [l, left[0]], [left[0], l])
            ans += r - left[0]
            imp[left[0]] += left[0] - left[1]

        return ans + max(imp)
