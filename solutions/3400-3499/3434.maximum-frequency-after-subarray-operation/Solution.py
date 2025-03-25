class Solution(object):
    def maxFrequency(self, nums, k):
        orig = nums.count(k)
        max_gain = 0
        for m in range(1, 51):
            if m == k:
                continue
            current = max_current = 0
            for num in nums:
                if num == m:
                    current += 1
                elif num == k:
                    current -= 1
                current = max(current, 0)
                max_current = max(max_current, current)
            max_gain = max(max_gain, max_current)
        return orig + max_gain