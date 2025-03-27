class Solution:
    def subsequencesWithMiddleMode(self, A: List[int]) -> int:
        MOD = 10**9 + 7
        N = len(A)
        ans = 0
        pre = Counter()
        suf = Counter(A)

        for i, v in enumerate(A):
            suf[v] -= 1

            left = i
            right = N - 1 - i
            ans += comb(left, 2) * comb(right, 2)
            ans -= comb(left - pre[v], 2) * comb(right - suf[v], 2)

            for x in pre | suf:
                if x == v:
                    continue

                # Subtract 221 cases: bb/a/ac, ac/a/bb, ab/a/bc, bc/a/ab
                ans -= comb(pre[x], 2) * suf[v] * (right - suf[v] - suf[x])
                ans -= comb(suf[x], 2) * pre[v] * (left - pre[v] - pre[x])
                ans -= pre[v] * pre[x] * suf[x] * (right - suf[v] - suf[x])
                ans -= suf[v] * suf[x] * pre[x] * (left - pre[v] - pre[x])

                # Subtract 32 cases: bb/a/ab, ab/a/bb
                ans -= comb(pre[x], 2) * suf[v] * suf[x]
                ans -= comb(suf[x], 2) * pre[v] * pre[x]

            pre[v] += 1

        return ans % MOD
