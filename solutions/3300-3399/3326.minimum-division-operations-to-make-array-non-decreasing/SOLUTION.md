# Solution: Preprocessing + Greedy

According to the problem description,

If an integer $x$ is a prime number, then its largest proper divisor is $1$, so $x / 1 = x$, meaning $x$ cannot be divided further.

If an integer $x$ is not a prime number, we assume the largest proper divisor of $x$ is $y$, then $x / y$ must be a prime number. Therefore, we find the smallest prime factor $\textit{lpf}[x]$ such that $x \bmod \textit{lpf}[x] = 0$, making $x$ become $\textit{lpf}[x]$, at which point it cannot be divided further.

Thus, we can preprocess the smallest prime factor for each integer from $1$ to $10^6$. Then, we traverse the array from right to left. If the current element is greater than the next element, we change the current element to its smallest prime factor. If after changing the current element to its smallest prime factor it is still greater than the next element, it means the array cannot be made non-decreasing, and we return $-1$. Otherwise, we increment the operation count by one. Continue traversing until the entire array is processed.

The time complexity for preprocessing is $O(M \times \log \log M)$, where $M = 10^6$. The time complexity for traversing the array is $O(n)$, where $n$ is the length of the array. The space complexity is $O(M)$.
    
# Code    


#### Python3

```python
mx = 10**6 + 1
lpf = [0] * (mx + 1)
for i in range(2, mx + 1):
    if lpf[i] == 0:
        for j in range(i, mx + 1, i):
            if lpf[j] == 0:
                lpf[j] = i


class Solution:
    def minOperations(self, nums: List[int]) -> int:
        ans = 0
        for i in range(len(nums) - 2, -1, -1):
            if nums[i] > nums[i + 1]:
                nums[i] = lpf[nums[i]]
                if nums[i] > nums[i + 1]:
                    return -1
                ans += 1
        return ans
```

#### Java

```java
class Solution {
    private static final int MX = (int) 1e6 + 1;
    private static final int[] LPF = new int[MX + 1];
    static {
        for (int i = 2; i <= MX; ++i) {
            for (int j = i; j <= MX; j += i) {
                if (LPF[j] == 0) {
                    LPF[j] = i;
                }
            }
        }
    }
    public int minOperations(int[] nums) {
        int ans = 0;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] > nums[i + 1]) {
                nums[i] = LPF[nums[i]];
                if (nums[i] > nums[i + 1]) {
                    return -1;
                }
                ans++;
            }
        }
        return ans;
    }
}
```

#### C++

```cpp
const int MX = 1e6;
int LPF[MX + 1];

auto init = [] {
    for (int i = 2; i <= MX; i++) {
        if (LPF[i] == 0) {
            for (int j = i; j <= MX; j += i) {
                if (LPF[j] == 0) {
                    LPF[j] = i;
                }
            }
        }
    }
    return 0;
}();

class Solution {
public:
    int minOperations(vector<int>& nums) {
        int ans = 0;
        for (int i = nums.size() - 2; i >= 0; i--) {
            if (nums[i] > nums[i + 1]) {
                nums[i] = LPF[nums[i]];
                if (nums[i] > nums[i + 1]) {
                    return -1;
                }
                ans++;
            }
        }
        return ans;
    }
};
```

#### Go

```go
const mx int = 1e6

var lpf = [mx + 1]int{}

func init() {
	for i := 2; i <= mx; i++ {
		if lpf[i] == 0 {
			for j := i; j <= mx; j += i {
				if lpf[j] == 0 {
					lpf[j] = i
				}
			}
		}
	}
}

func minOperations(nums []int) (ans int) {
	for i := len(nums) - 2; i >= 0; i-- {
		if nums[i] > nums[i+1] {
			nums[i] = lpf[nums[i]]
			if nums[i] > nums[i+1] {
				return -1
			}
			ans++
		}
	}
	return
}
```

#### TypeScript

```ts
const mx = 10 ** 6;
const lpf = Array(mx + 1).fill(0);
for (let i = 2; i <= mx; ++i) {
    for (let j = i; j <= mx; j += i) {
        if (lpf[j] === 0) {
            lpf[j] = i;
        }
    }
}

function minOperations(nums: number[]): number {
    let ans = 0;
    for (let i = nums.length - 2; ~i; --i) {
        if (nums[i] > nums[i + 1]) {
            nums[i] = lpf[nums[i]];
            if (nums[i] > nums[i + 1]) {
                return -1;
            }
            ++ans;
        }
    }
    return ans;
}
```

