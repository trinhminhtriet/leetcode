# Solution: Simulation

We can traverse the array $\textit{nums}$. For each number $x$, we calculate the sum of its digits $y$. The minimum value among all $y$ is the answer.

The time complexity is $O(n \times \log M)$, where $n$ and $M$ are the length of the array $\textit{nums}$ and the maximum value in the array, respectively. The space complexity is $O(1)$.
    
# Code    


#### Python3

```python
class Solution:
    def minElement(self, nums: List[int]) -> int:
        return min(sum(int(b) for b in str(x)) for x in nums)
```

#### Java

```java
class Solution {
    public int minElement(int[] nums) {
        int ans = 100;
        for (int x : nums) {
            int y = 0;
            for (; x > 0; x /= 10) {
                y += x % 10;
            }
            ans = Math.min(ans, y);
        }
        return ans;
    }
}
```

#### C++

```cpp
class Solution {
public:
    int minElement(vector<int>& nums) {
        int ans = 100;
        for (int x : nums) {
            int y = 0;
            for (; x > 0; x /= 10) {
                y += x % 10;
            }
            ans = min(ans, y);
        }
        return ans;
    }
};
```

#### Go

```go
func minElement(nums []int) int {
	ans := 100
	for _, x := range nums {
		y := 0
		for ; x > 0; x /= 10 {
			y += x % 10
		}
		ans = min(ans, y)
	}
	return ans
}
```

#### TypeScript

```ts
function minElement(nums: number[]): number {
    let ans: number = 100;
    for (let x of nums) {
        let y = 0;
        for (; x; x = Math.floor(x / 10)) {
            y += x % 10;
        }
        ans = Math.min(ans, y);
    }
    return ans;
}
```

