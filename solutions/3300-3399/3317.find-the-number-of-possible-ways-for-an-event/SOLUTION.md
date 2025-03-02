# Solution: Dynamic Programming

We define $f[i][j]$ to represent the number of ways to arrange the first $i$ performers into $j$ programs. Initially, $f[0][0] = 1$, and the rest $f[i][j] = 0$.

For $f[i][j]$, where $1 \leq i \leq n$ and $1 \leq j \leq x$, we consider the $i$-th performer:

-   If the performer is assigned to a program that already has performers, there are $j$ choices, i.e., $f[i - 1][j] \times j$;
-   If the performer is assigned to a program that has no performers, there are $x - (j - 1)$ choices, i.e., $f[i - 1][j - 1] \times (x - (j - 1))$.

So the state transition equation is:

$$
f[i][j] = f[i - 1][j] \times j + f[i - 1][j - 1] \times (x - (j - 1))
$$

For each $j$, there are $y^j$ choices, so the final answer is:

$$
\sum_{j = 1}^{x} f[n][j] \times y^j
$$

Note that since the answer can be very large, we need to take the modulo $10^9 + 7$.

The time complexity is $O(n \times x)$, and the space complexity is $O(n \times x)$. Here, $n$ and $x$ represent the number of performers and the number of programs, respectively.
    
# Code    


#### Python3

```python
class Solution:
    def numberOfWays(self, n: int, x: int, y: int) -> int:
        mod = 10**9 + 7
        f = [[0] * (x + 1) for _ in range(n + 1)]
        f[0][0] = 1
        for i in range(1, n + 1):
            for j in range(1, x + 1):
                f[i][j] = (f[i - 1][j] * j + f[i - 1][j - 1] * (x - (j - 1))) % mod
        ans, p = 0, 1
        for j in range(1, x + 1):
            p = p * y % mod
            ans = (ans + f[n][j] * p) % mod
        return ans
```

#### Java

```java
class Solution {
    public int numberOfWays(int n, int x, int y) {
        final int mod = (int) 1e9 + 7;
        long[][] f = new long[n + 1][x + 1];
        f[0][0] = 1;
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= x; ++j) {
                f[i][j] = (f[i - 1][j] * j % mod + f[i - 1][j - 1] * (x - (j - 1) % mod)) % mod;
            }
        }
        long ans = 0, p = 1;
        for (int j = 1; j <= x; ++j) {
            p = p * y % mod;
            ans = (ans + f[n][j] * p) % mod;
        }
        return (int) ans;
    }
}
```

#### C++

```cpp
class Solution {
public:
    int numberOfWays(int n, int x, int y) {
        const int mod = 1e9 + 7;
        long long f[n + 1][x + 1];
        memset(f, 0, sizeof(f));
        f[0][0] = 1;
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= x; ++j) {
                f[i][j] = (f[i - 1][j] * j % mod + f[i - 1][j - 1] * (x - (j - 1) % mod)) % mod;
            }
        }
        long long ans = 0, p = 1;
        for (int j = 1; j <= x; ++j) {
            p = p * y % mod;
            ans = (ans + f[n][j] * p) % mod;
        }
        return ans;
    }
};
```

#### Go

```go
func numberOfWays(n int, x int, y int) int {
	const mod int = 1e9 + 7
	f := make([][]int, n+1)
	for i := range f {
		f[i] = make([]int, x+1)
	}
	f[0][0] = 1
	for i := 1; i <= n; i++ {
		for j := 1; j <= x; j++ {
			f[i][j] = (f[i-1][j]*j%mod + f[i-1][j-1]*(x-(j-1))%mod) % mod
		}
	}
	ans, p := 0, 1
	for j := 1; j <= x; j++ {
		p = p * y % mod
		ans = (ans + f[n][j]*p%mod) % mod
	}
	return ans
}
```

#### TypeScript

```ts
function numberOfWays(n: number, x: number, y: number): number {
    const mod = BigInt(10 ** 9 + 7);
    const f: bigint[][] = Array.from({ length: n + 1 }, () => Array(x + 1).fill(0n));
    f[0][0] = 1n;
    for (let i = 1; i <= n; ++i) {
        for (let j = 1; j <= x; ++j) {
            f[i][j] = (f[i - 1][j] * BigInt(j) + f[i - 1][j - 1] * BigInt(x - (j - 1))) % mod;
        }
    }
    let [ans, p] = [0n, 1n];
    for (let j = 1; j <= x; ++j) {
        p = (p * BigInt(y)) % mod;
        ans = (ans + f[n][j] * p) % mod;
    }
    return Number(ans);
}
```

