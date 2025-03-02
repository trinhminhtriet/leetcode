### Solution 1
    
# Code    


#### Python3

```python
class Solution:
    def maxScore(self, nums: List[int]) -> int:
        n = len(nums)
        suf_gcd = [0] * (n + 1)
        suf_lcm = [0] * n + [1]
        for i in range(n - 1, -1, -1):
            suf_gcd[i] = gcd(suf_gcd[i + 1], nums[i])
            suf_lcm[i] = lcm(suf_lcm[i + 1], nums[i])
        ans = suf_gcd[0] * suf_lcm[0]
        pre_gcd, pre_lcm = 0, 1
        for i, x in enumerate(nums):
            ans = max(ans, gcd(pre_gcd, suf_gcd[i + 1]) * lcm(pre_lcm, suf_lcm[i + 1]))
            pre_gcd = gcd(pre_gcd, x)
            pre_lcm = lcm(pre_lcm, x)
        return ans
```

#### Java

```java
class Solution {
    public long maxScore(int[] nums) {
        int n = nums.length;
        long[] sufGcd = new long[n + 1];
        long[] sufLcm = new long[n + 1];
        sufLcm[n] = 1;
        for (int i = n - 1; i >= 0; --i) {
            sufGcd[i] = gcd(sufGcd[i + 1], nums[i]);
            sufLcm[i] = lcm(sufLcm[i + 1], nums[i]);
        }
        long ans = sufGcd[0] * sufLcm[0];
        long preGcd = 0, preLcm = 1;
        for (int i = 0; i < n; ++i) {
            ans = Math.max(ans, gcd(preGcd, sufGcd[i + 1]) * lcm(preLcm, sufLcm[i + 1]));
            preGcd = gcd(preGcd, nums[i]);
            preLcm = lcm(preLcm, nums[i]);
        }
        return ans;
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }
}
```

#### C++

```cpp
class Solution {
public:
    long long maxScore(vector<int>& nums) {
        int n = nums.size();
        vector<long long> sufGcd(n + 1, 0);
        vector<long long> sufLcm(n + 1, 1);
        for (int i = n - 1; i >= 0; --i) {
            sufGcd[i] = gcd(sufGcd[i + 1], nums[i]);
            sufLcm[i] = lcm(sufLcm[i + 1], nums[i]);
        }

        long long ans = sufGcd[0] * sufLcm[0];
        long long preGcd = 0, preLcm = 1;
        for (int i = 0; i < n; ++i) {
            ans = max(ans, gcd(preGcd, sufGcd[i + 1]) * lcm(preLcm, sufLcm[i + 1]));
            preGcd = gcd(preGcd, nums[i]);
            preLcm = lcm(preLcm, nums[i]);
        }
        return ans;
    }
};
```

#### Go

```go
func maxScore(nums []int) int64 {
	n := len(nums)
	sufGcd := make([]int64, n+1)
	sufLcm := make([]int64, n+1)
	sufLcm[n] = 1
	for i := n - 1; i >= 0; i-- {
		sufGcd[i] = gcd(sufGcd[i+1], int64(nums[i]))
		sufLcm[i] = lcm(sufLcm[i+1], int64(nums[i]))
	}

	ans := sufGcd[0] * sufLcm[0]
	preGcd, preLcm := int64(0), int64(1)
	for i := 0; i < n; i++ {
		ans = max(ans, gcd(preGcd, sufGcd[i+1])*lcm(preLcm, sufLcm[i+1]))
		preGcd = gcd(preGcd, int64(nums[i]))
		preLcm = lcm(preLcm, int64(nums[i]))
	}
	return ans
}

func gcd(a, b int64) int64 {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}

func lcm(a, b int64) int64 {
	return a / gcd(a, b) * b
}
```

#### TypeScript

```ts
function maxScore(nums: number[]): number {
    const n = nums.length;
    const sufGcd: number[] = Array(n + 1).fill(0);
    const sufLcm: number[] = Array(n + 1).fill(1);
    for (let i = n - 1; i >= 0; i--) {
        sufGcd[i] = gcd(sufGcd[i + 1], nums[i]);
        sufLcm[i] = lcm(sufLcm[i + 1], nums[i]);
    }

    let ans = sufGcd[0] * sufLcm[0];
    let preGcd = 0,
        preLcm = 1;
    for (let i = 0; i < n; i++) {
        ans = Math.max(ans, gcd(preGcd, sufGcd[i + 1]) * lcm(preLcm, sufLcm[i + 1]));
        preGcd = gcd(preGcd, nums[i]);
        preLcm = lcm(preLcm, nums[i]);
    }
    return ans;
}

function gcd(a: number, b: number): number {
    return b === 0 ? a : gcd(b, a % b);
}

function lcm(a: number, b: number): number {
    return (a / gcd(a, b)) * b;
}
```

