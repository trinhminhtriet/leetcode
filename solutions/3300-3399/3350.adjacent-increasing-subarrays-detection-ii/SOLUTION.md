### Solution 1
    
# Code    


#### Python3

```python
class Solution:
    def maxIncreasingSubarrays(self, nums: List[int]) -> int:
        ans = pre = cur = 0
        for i, x in enumerate(nums):
            cur += 1
            if i == len(nums) - 1 or x >= nums[i + 1]:
                ans = max(ans, cur // 2, min(pre, cur))
                pre, cur = cur, 0
        return ans
```

#### Java

```java
class Solution {
    public int maxIncreasingSubarrays(List<Integer> nums) {
        int ans = 0, pre = 0, cur = 0;
        int n = nums.size();
        for (int i = 0; i < n; ++i) {
            ++cur;
            if (i == n - 1 || nums.get(i) >= nums.get(i + 1)) {
                ans = Math.max(ans, Math.max(cur / 2, Math.min(pre, cur)));
                pre = cur;
                cur = 0;
            }
        }
        return ans;
    }
}
```

#### C++

```cpp
class Solution {
public:
    int maxIncreasingSubarrays(vector<int>& nums) {
        int ans = 0, pre = 0, cur = 0;
        int n = nums.size();
        for (int i = 0; i < n; ++i) {
            ++cur;
            if (i == n - 1 || nums[i] >= nums[i + 1]) {
                ans = max({ans, cur / 2, min(pre, cur)});
                pre = cur;
                cur = 0;
            }
        }
        return ans;
    }
};
```

#### Go

```go
func maxIncreasingSubarrays(nums []int) (ans int) {
	pre, cur := 0, 0
	for i, x := range nums {
		cur++
		if i == len(nums)-1 || x >= nums[i+1] {
			ans = max(ans, max(cur/2, min(pre, cur)))
			pre, cur = cur, 0
		}
	}
	return
}
```

#### TypeScript

```ts
function maxIncreasingSubarrays(nums: number[]): number {
    let [ans, pre, cur] = [0, 0, 0];
    const n = nums.length;
    for (let i = 0; i < n; ++i) {
        ++cur;
        if (i === n - 1 || nums[i] >= nums[i + 1]) {
            ans = Math.max(ans, (cur / 2) | 0, Math.min(pre, cur));
            [pre, cur] = [cur, 0];
        }
    }
    return ans;
}
```

