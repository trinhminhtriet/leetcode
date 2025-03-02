# Solution: Sorting + Greedy

We can sort the maximum heights of the towers in descending order, then allocate the heights one by one starting from the maximum height. Use a variable $mx$ to record the current maximum allocated height.

If the current height $x$ is greater than $mx - 1$, update $x$ to $mx - 1$. If $x$ is less than or equal to $0$, it means the height cannot be allocated, and we directly return $-1$. Otherwise, we add $x$ to the answer and update $mx$ to $x$.

Finally, return the answer.

The time complexity is $O(n \times \log n)$, and the space complexity is $O(\log n)$. Here, $n$ is the length of the array $\textit{maximumHeight}$.
    
# Code    


#### Python3

```python
class Solution:
    def maximumTotalSum(self, maximumHeight: List[int]) -> int:
        maximumHeight.sort()
        ans, mx = 0, inf
        for x in maximumHeight[::-1]:
            x = min(x, mx - 1)
            if x <= 0:
                return -1
            ans += x
            mx = x
        return ans
```

#### Java

```java
class Solution {
    public long maximumTotalSum(int[] maximumHeight) {
        long ans = 0;
        int mx = 1 << 30;
        Arrays.sort(maximumHeight);
        for (int i = maximumHeight.length - 1; i >= 0; --i) {
            int x = Math.min(maximumHeight[i], mx - 1);
            if (x <= 0) {
                return -1;
            }
            ans += x;
            mx = x;
        }
        return ans;
    }
}
```

#### C++

```cpp
class Solution {
public:
    long long maximumTotalSum(vector<int>& maximumHeight) {
        ranges::sort(maximumHeight, greater<int>());
        long long ans = 0;
        int mx = 1 << 30;
        for (int x : maximumHeight) {
            x = min(x, mx - 1);
            if (x <= 0) {
                return -1;
            }
            ans += x;
            mx = x;
        }
        return ans;
    }
};
```

#### Go

```go
func maximumTotalSum(maximumHeight []int) int64 {
	slices.SortFunc(maximumHeight, func(a, b int) int { return b - a })
	ans := int64(0)
	mx := 1 << 30
	for _, x := range maximumHeight {
		x = min(x, mx-1)
		if x <= 0 {
			return -1
		}
		ans += int64(x)
		mx = x
	}
	return ans
}
```

#### TypeScript

```ts
function maximumTotalSum(maximumHeight: number[]): number {
    maximumHeight.sort((a, b) => a - b).reverse();
    let ans: number = 0;
    let mx: number = Infinity;
    for (let x of maximumHeight) {
        x = Math.min(x, mx - 1);
        if (x <= 0) {
            return -1;
        }
        ans += x;
        mx = x;
    }
    return ans;
}
```

