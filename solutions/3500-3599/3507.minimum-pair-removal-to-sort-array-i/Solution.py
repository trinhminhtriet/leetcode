class Solution:
    def minimumPairRemoval(self, nums: List[int]) -> int:
        def is_non_decreasing(arr):
            return all(arr[i] <= arr[i+1] for i in range(len(arr) - 1))

        operations = 0

        while not is_non_decreasing(nums):
            min_sum = float('inf')
            min_index = -1

            for i in range(len(nums) - 1):
                pair_sum = nums[i] + nums[i+1]
                if pair_sum < min_sum:
                    min_sum = pair_sum
                    min_index = i

            # Replace the pair with their sum
            new_num = nums[min_index] + nums[min_index + 1]
            nums = nums[:min_index] + [new_num] + nums[min_index + 2:]
            operations += 1

        return operations
