# Solution: Greedy + Sorting

We can sort the array $\textit{nums}$ and then consider each element $x$ from left to right.

For the first element, we can greedily change it to $x - k$, making $x$ as small as possible to leave more space for subsequent elements. We use the variable $\textit{pre}$ to track the maximum value of the elements used so far, initialized to negative infinity.

For subsequent elements $x$, we can greedily change it to $\min(x + k, \max(x - k, \textit{pre} + 1))$. Here, $\max(x - k, \textit{pre} + 1)$ means we try to make $x$ as small as possible but not smaller than $\textit{pre} + 1$. If this value exists and is less than $x + k$, we can change $x$ to this value, increment the count of distinct elements, and update $\textit{pre}$ to this value.

After traversing the array, we obtain the maximum number of distinct elements.

The time complexity is $O(n \times \log n)$, and the space complexity is $O(\log n)$. Here, $n$ is the length of the array $\textit{nums}$.

## Code


#### Python3

```python
class Solution:
    def maxDistinctElements(self, nums: List[int], k: int) -> int:
        nums.sort()
        ans = 0
        pre = -inf
        for x in nums:
            cur = min(x + k, max(x - k, pre + 1))
            if cur > pre:
                ans += 1
                pre = cur
        return ans
```

#### Java

```java
class Solution {
    public int maxDistinctElements(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int ans = 0, pre = Integer.MIN_VALUE;
        for (int x : nums) {
            int cur = Math.min(x + k, Math.max(x - k, pre + 1));
            if (cur > pre) {
                ++ans;
                pre = cur;
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
    int maxDistinctElements(vector<int>& nums, int k) {
        ranges::sort(nums);
        int ans = 0, pre = INT_MIN;
        for (int x : nums) {
            int cur = min(x + k, max(x - k, pre + 1));
            if (cur > pre) {
                ++ans;
                pre = cur;
            }
        }
        return ans;
    }
};
```

#### Go

```go
func maxDistinctElements(nums []int, k int) (ans int) {
	sort.Ints(nums)
	pre := math.MinInt32
	for _, x := range nums {
		cur := min(x+k, max(x-k, pre+1))
		if cur > pre {
			ans++
			pre = cur
		}
	}
	return
}
```

#### TypeScript

```ts
function maxDistinctElements(nums: number[], k: number): number {
    nums.sort((a, b) => a - b);
    let [ans, pre] = [0, -Infinity];
    for (const x of nums) {
        const cur = Math.min(x + k, Math.max(x - k, pre + 1));
        if (cur > pre) {
            ++ans;
            pre = cur;
        }
    }
    return ans;
}
```


## Contact information

If you have any questions or suggestions, please feel free to contact me at https://trinhminhtriet.com/
- Website: [trinhminhtriet.com](https://trinhminhtriet.com/)
- Github: [trinhminhtriet](https://github.com/trinhminhtriet)
- LinkedIn: [trinhminhtriet](https://www.linkedin.com/in/triet-trinh/)
- Twitter: [trinhminhtriet](https://twitter.com/trinhminhtriet)
- Email: [contact@trinhminhtriet.com](mailto:contact@trinhminhtriet.com)
