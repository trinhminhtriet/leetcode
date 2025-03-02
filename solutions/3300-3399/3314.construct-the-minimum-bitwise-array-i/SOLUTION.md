# Solution: Bit Manipulation

For an integer $a$, the result of $a \lor (a + 1)$ is always odd. Therefore, if $\text{nums[i]}$ is even, then $\text{ans}[i]$ does not exist, and we directly return $-1$. In this problem, $\textit{nums}[i]$ is a prime number, so to check if it is even, we only need to check if it equals $2$.

If $\text{nums[i]}$ is odd, suppose $\text{nums[i]} = \text{0b1101101}$. Since $a \lor (a + 1) = \text{nums[i]}$, this is equivalent to changing the last $0$ bit of $a$ to $1$. To solve for $a$, we need to change the bit after the last $0$ in $\text{nums[i]}$ to $0$. We start traversing from the least significant bit (index $1$) and find the first $0$ bit. If it is at position $i$, we change the $(i - 1)$-th bit of $\text{nums[i]}$ to $1$, i.e., $\text{ans}[i] = \text{nums[i]} \oplus 2^{i - 1}$.

By traversing all elements in $\text{nums}$, we can obtain the answer.

The time complexity is $O(n \times \log M)$, where $n$ and $M$ are the length of the array $\text{nums}$ and the maximum value in the array, respectively. Ignoring the space consumption of the answer array, the space complexity is $O(1)$.
    
# Code    


#### Python3

```python
class Solution:
    def minBitwiseArray(self, nums: List[int]) -> List[int]:
        ans = []
        for x in nums:
            if x == 2:
                ans.append(-1)
            else:
                for i in range(1, 32):
                    if x >> i & 1 ^ 1:
                        ans.append(x ^ 1 << (i - 1))
                        break
        return ans
```

#### Java

```java
class Solution {
    public int[] minBitwiseArray(List<Integer> nums) {
        int n = nums.size();
        int[] ans = new int[n];
        for (int i = 0; i < n; ++i) {
            int x = nums.get(i);
            if (x == 2) {
                ans[i] = -1;
            } else {
                for (int j = 1; j < 32; ++j) {
                    if ((x >> j & 1) == 0) {
                        ans[i] = x ^ 1 << (j - 1);
                        break;
                    }
                }
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
    vector<int> minBitwiseArray(vector<int>& nums) {
        vector<int> ans;
        for (int x : nums) {
            if (x == 2) {
                ans.push_back(-1);
            } else {
                for (int i = 1; i < 32; ++i) {
                    if (x >> i & 1 ^ 1) {
                        ans.push_back(x ^ 1 << (i - 1));
                        break;
                    }
                }
            }
        }
        return ans;
    }
};
```

#### Go

```go
func minBitwiseArray(nums []int) (ans []int) {
	for _, x := range nums {
		if x == 2 {
			ans = append(ans, -1)
		} else {
			for i := 1; i < 32; i++ {
				if x>>i&1 == 0 {
					ans = append(ans, x^1<<(i-1))
					break
				}
			}
		}
	}
	return
}
```

#### TypeScript

```ts
function minBitwiseArray(nums: number[]): number[] {
    const ans: number[] = [];
    for (const x of nums) {
        if (x === 2) {
            ans.push(-1);
        } else {
            for (let i = 1; i < 32; ++i) {
                if (((x >> i) & 1) ^ 1) {
                    ans.push(x ^ (1 << (i - 1)));
                    break;
                }
            }
        }
    }
    return ans;
}
```

