### Solution 1
    
# Code    


#### Python3

```python
class Solution:
    def sumOfGoodSubsequences(self, nums: List[int]) -> int:
        mod = 10**9 + 7
        f = defaultdict(int)
        g = defaultdict(int)
        for x in nums:
            f[x] += x
            g[x] += 1
            f[x] += f[x - 1] + g[x - 1] * x
            g[x] += g[x - 1]
            f[x] += f[x + 1] + g[x + 1] * x
            g[x] += g[x + 1]
        return sum(f.values()) % mod
```

#### Java

```java
class Solution {
    public int sumOfGoodSubsequences(int[] nums) {
        final int mod = (int) 1e9 + 7;
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }
        long[] f = new long[mx + 1];
        long[] g = new long[mx + 1];
        for (int x : nums) {
            f[x] += x;
            g[x] += 1;
            if (x > 0) {
                f[x] = (f[x] + f[x - 1] + g[x - 1] * x % mod) % mod;
                g[x] = (g[x] + g[x - 1]) % mod;
            }
            if (x + 1 <= mx) {
                f[x] = (f[x] + f[x + 1] + g[x + 1] * x % mod) % mod;
                g[x] = (g[x] + g[x + 1]) % mod;
            }
        }
        long ans = 0;
        for (long x : f) {
            ans = (ans + x) % mod;
        }
        return (int) ans;
    }
}
```

#### C++

```cpp
class Solution {
public:
    int sumOfGoodSubsequences(vector<int>& nums) {
        const int mod = 1e9 + 7;
        int mx = ranges::max(nums);

        vector<long long> f(mx + 1), g(mx + 1);
        for (int x : nums) {
            f[x] += x;
            g[x] += 1;

            if (x > 0) {
                f[x] = (f[x] + f[x - 1] + g[x - 1] * x % mod) % mod;
                g[x] = (g[x] + g[x - 1]) % mod;
            }

            if (x + 1 <= mx) {
                f[x] = (f[x] + f[x + 1] + g[x + 1] * x % mod) % mod;
                g[x] = (g[x] + g[x + 1]) % mod;
            }
        }

        return accumulate(f.begin(), f.end(), 0LL) % mod;
    }
};
```

#### Go

```go
func sumOfGoodSubsequences(nums []int) (ans int) {
	mod := int(1e9 + 7)
	mx := slices.Max(nums)

	f := make([]int, mx+1)
	g := make([]int, mx+1)

	for _, x := range nums {
		f[x] += x
		g[x] += 1

		if x > 0 {
			f[x] = (f[x] + f[x-1] + g[x-1]*x%mod) % mod
			g[x] = (g[x] + g[x-1]) % mod
		}

		if x+1 <= mx {
			f[x] = (f[x] + f[x+1] + g[x+1]*x%mod) % mod
			g[x] = (g[x] + g[x+1]) % mod
		}
	}

	for _, x := range f {
		ans = (ans + x) % mod
	}
	return
}
```

#### TypeScript

```ts
function sumOfGoodSubsequences(nums: number[]): number {
    const mod = 10 ** 9 + 7;
    const mx = Math.max(...nums);
    const f: number[] = Array(mx + 1).fill(0);
    const g: number[] = Array(mx + 1).fill(0);
    for (const x of nums) {
        f[x] += x;
        g[x] += 1;
        if (x > 0) {
            f[x] = (f[x] + f[x - 1] + ((g[x - 1] * x) % mod)) % mod;
            g[x] = (g[x] + g[x - 1]) % mod;
        }
        if (x + 1 <= mx) {
            f[x] = (f[x] + f[x + 1] + ((g[x + 1] * x) % mod)) % mod;
            g[x] = (g[x] + g[x + 1]) % mod;
        }
    }
    return f.reduce((acc, cur) => (acc + cur) % mod, 0);
}
```

