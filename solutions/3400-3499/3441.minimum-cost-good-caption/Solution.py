class Solution:
    def minCostGoodCaption(self, caption: str) -> str:
        n = len(caption)
        if n < 3:
            return ""

        @cache
        def fn(i):
            """Return ops, character and repetition at index i."""
            if i == n:
                return 0, "", 0
            v, c, r = inf, "", 0
            sub = ""
            for k in range(i+2, min(n, i+5)):
                mp = {}
                for x in caption[i: k+1]:
                    mp[x] = sum(abs(ord(x) - ord(v)) for v in caption[i: k+1])
                vv = min(mp.values())
                cc = min(c for c, f in mp.items() if f == vv)
                rr = k-i+1
                pv, pc, pr = fn(k+1)
                cand = (cc*rr + pc*pr)[:6]
                if vv+pv < v or vv+pv == v and cand < sub:
                    v, c, r = vv+pv, cc, rr
                    sub = cand
            return v, c, r

        ans = []
        i = 0
        while i < n:
            _, c, r = fn(i)
            ans.append(c * r)
            i += r
        return ''.join(ans)
