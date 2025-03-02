# Solution: Preprocessing + Binary Search

We can roughly estimate the maximum value of $n$. For $j \lor k$, the sum of the results is approximately $n^2 (n - 1) / 2$. Multiplying this by each $i \in [0, n)$, the result is approximately $(n-1)^5 / 4$. To ensure $(n - 1)^5 / 4 \leq s$, we have $n \leq 1320$.

Therefore, we can preprocess $f[n] = \sum_{i=0}^{n-1} \sum_{j=0}^{i} (i \lor j)$, and then use binary search to find the largest $n$ such that $f[n-1] \cdot (n-1) \cdot n / 2 \leq s$.

In terms of time complexity, the preprocessing has a time complexity of $O(n^2)$, and the binary search has a time complexity of $O(\log n)$. Therefore, the total time complexity is $O(n^2 + \log n)$. The space complexity is $O(n)$.
    
# Code    


#### Python3

```python
mx = 1330
f = [0] * mx
for i in range(1, mx):
    f[i] = f[i - 1] + i
    for j in range(i):
        f[i] += 2 * (i | j)


class Solution:
    def maxSizedArray(self, s: int) -> int:
        l, r = 1, mx
        while l < r:
            m = (l + r + 1) >> 1
            if f[m - 1] * (m - 1) * m // 2 <= s:
                l = m
            else:
                r = m - 1
        return l
```

#### Java

```java
class Solution {
    private static final int MX = 1330;
    private static final long[] f = new long[MX];
    static {
        for (int i = 1; i < MX; ++i) {
            f[i] = f[i - 1] + i;
            for (int j = 0; j < i; ++j) {
                f[i] += 2 * (i | j);
            }
        }
    }
    public int maxSizedArray(long s) {
        int l = 1, r = MX;
        while (l < r) {
            int m = (l + r + 1) >> 1;
            if (f[m - 1] * (m - 1) * m / 2 <= s) {
                l = m;
            } else {
                r = m - 1;
            }
        }
        return l;
    }
}
```

#### C++

```cpp
const int MX = 1330;
long long f[MX];
auto init = [] {
    f[0] = 0;
    for (int i = 1; i < MX; ++i) {
        f[i] = f[i - 1] + i;
        for (int j = 0; j < i; ++j) {
            f[i] += 2 * (i | j);
        }
    }
    return 0;
}();

class Solution {
public:
    int maxSizedArray(long long s) {
        int l = 1, r = MX;
        while (l < r) {
            int m = (l + r + 1) >> 1;
            if (f[m - 1] * (m - 1) * m / 2 <= s) {
                l = m;
            } else {
                r = m - 1;
            }
        }
        return l;
    }
};
```

#### Go

```go
const MX = 1330

var f [MX]int64

func init() {
	f[0] = 0
	for i := 1; i < MX; i++ {
		f[i] = f[i-1] + int64(i)
		for j := 0; j < i; j++ {
			f[i] += 2 * int64(i|j)
		}
	}
}

func maxSizedArray(s int64) int {
	l, r := 1, MX
	for l < r {
		m := (l + r + 1) >> 1
		if f[m-1]*int64(m-1)*int64(m)/2 <= s {
			l = m
		} else {
			r = m - 1
		}
	}
	return l
}
```

#### TypeScript

```ts
const MX = 1330;
const f: bigint[] = Array(MX).fill(0n);
(() => {
    f[0] = 0n;
    for (let i = 1; i < MX; i++) {
        f[i] = f[i - 1] + BigInt(i);
        for (let j = 0; j < i; j++) {
            f[i] += BigInt(2) * BigInt(i | j);
        }
    }
})();

function maxSizedArray(s: number): number {
    let l = 1,
        r = MX;
    const target = BigInt(s);

    while (l < r) {
        const m = (l + r + 1) >> 1;
        if ((f[m - 1] * BigInt(m - 1) * BigInt(m)) / BigInt(2) <= target) {
            l = m;
        } else {
            r = m - 1;
        }
    }
    return l;
}
```

