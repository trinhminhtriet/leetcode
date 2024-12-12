from math import gcd
from typing import List


class Solution:
    def countPairs(self, nums: List[int], k: int) -> int:
        ans = 0
        gcds = {}
        
        for num in nums:
            gcd_num = gcd(num, k)
            for gcd_val, count in gcds.items():
                if gcd_num * gcd_val % k == 0:
                    ans += count
            gcds[gcd_num] = gcds.get(gcd_num, 0) + 1
        
        return ans