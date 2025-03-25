class Solution:
    def find_number_of_subarrays(self, l, r, k):
        l = l - 1
        r = min(k, r)

        c1 = r
        
        c2_elements = k - r if l >= k - r else l
        c2 = r * c2_elements

        left_sub_arr_remain_elements = l - c2_elements if l >= c2_elements else 0

        c3 = max(((r - 1) * (r) // 2) - ((r - left_sub_arr_remain_elements - 1) * (r- left_sub_arr_remain_elements) // 2), 0)

        if left_sub_arr_remain_elements >= r - 1:
            c3 = (r - 1) * (r) // 2

        return c1 + c2 + c3

    def sum_of_subarray_mins_or_maxs_atmostK(self, arr, k, isMinSubarrSum):
        monotonic_stack = []
        ans = 0
        for ind, num in enumerate(arr):
            while monotonic_stack and (arr[monotonic_stack[-1]] > num if isMinSubarrSum else arr[monotonic_stack[-1]] < num):
                poppedInd = monotonic_stack.pop()
                val = arr[poppedInd]
                leftCertainInd = monotonic_stack[-1] + 1 if monotonic_stack else 0
                left_sub_array = (poppedInd + 1 - leftCertainInd)
                right_sub_array = ind  - poppedInd 
                
                ans += self.find_number_of_subarrays(left_sub_array, right_sub_array, k) * arr[poppedInd]

            monotonic_stack.append(ind)

        while monotonic_stack:
            poppedInd = monotonic_stack.pop()
            val = arr[poppedInd]
            leftCertainInd = monotonic_stack[-1] + 1 if monotonic_stack else 0
            left_sub_array = (poppedInd + 1 - leftCertainInd)
            right_sub_array = len(arr)  - poppedInd 
            
            ans += self.find_number_of_subarrays(left_sub_array, right_sub_array, k) * arr[poppedInd]
        
        return ans

    def minMaxSubarraySum(self, arr: List[int], k: int) -> int:
        return self.sum_of_subarray_mins_or_maxs_atmostK(arr, k, True) + self.sum_of_subarray_mins_or_maxs_atmostK(arr, k, False)