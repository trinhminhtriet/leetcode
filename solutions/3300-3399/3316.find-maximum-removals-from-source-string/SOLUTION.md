# Solution: Dynamic Programming

We define $f[i][j]$ to represent the maximum number of deletions in the first $i$ characters of $\textit{source}$ that match the first $j$ characters of $\textit{pattern}$. Initially, $f[0][0] = 0$, and the rest $f[i][j] = -\infty$.

For $f[i][j]$, we have two choices:

-   We can skip the $i$-th character of $\textit{source}$, in which case $f[i][j] = f[i-1][j] + \text{int}(i-1 \in \textit{targetIndices})$;
-   If $\textit{source}[i-1] = \textit{pattern}[j-1]$, we can match the $i$-th character of $\textit{source}$, in which case $f[i][j] = \max(f[i][j], f[i-1][j-1])$.

The final answer is $f[m][n]$.

The time complexity is $O(m \times n)$, and the space complexity is $O(m \times n)$. Here, $m$ and $n$ are the lengths of $\textit{source}$ and $\textit{pattern}$, respectively.
    
# Code    


#### Python3

```python
class Solution:
    def maxRemovals(self, source: str, pattern: str, targetIndices: List[int]) -> int:
        m, n = len(source), len(pattern)
        f = [[-inf] * (n + 1) for _ in range(m + 1)]
        f[0][0] = 0
        s = set(targetIndices)
        for i, c in enumerate(source, 1):
            for j in range(n + 1):
                f[i][j] = f[i - 1][j] + int((i - 1) in s)
                if j and c == pattern[j - 1]:
                    f[i][j] = max(f[i][j], f[i - 1][j - 1])
        return f[m][n]
```

#### Java

```java
class Solution {
    public int maxRemovals(String source, String pattern, int[] targetIndices) {
        int m = source.length(), n = pattern.length();
        int[][] f = new int[m + 1][n + 1];
        final int inf = Integer.MAX_VALUE / 2;
        for (var g : f) {
            Arrays.fill(g, -inf);
        }
        f[0][0] = 0;
        int[] s = new int[m];
        for (int i : targetIndices) {
            s[i] = 1;
        }
        for (int i = 1; i <= m; ++i) {
            for (int j = 0; j <= n; ++j) {
                f[i][j] = f[i - 1][j] + s[i - 1];
                if (j > 0 && source.charAt(i - 1) == pattern.charAt(j - 1)) {
                    f[i][j] = Math.max(f[i][j], f[i - 1][j - 1]);
                }
            }
        }
        return f[m][n];
    }
}
```

#### C++

```cpp
class Solution {
public:
    int maxRemovals(string source, string pattern, vector<int>& targetIndices) {
        int m = source.length(), n = pattern.length();
        vector<vector<int>> f(m + 1, vector<int>(n + 1, INT_MIN / 2));
        f[0][0] = 0;

        vector<int> s(m);
        for (int i : targetIndices) {
            s[i] = 1;
        }

        for (int i = 1; i <= m; ++i) {
            for (int j = 0; j <= n; ++j) {
                f[i][j] = f[i - 1][j] + s[i - 1];
                if (j > 0 && source[i - 1] == pattern[j - 1]) {
                    f[i][j] = max(f[i][j], f[i - 1][j - 1]);
                }
            }
        }

        return f[m][n];
    }
};
```

#### Go

```go
func maxRemovals(source string, pattern string, targetIndices []int) int {
	m, n := len(source), len(pattern)
	f := make([][]int, m+1)
	for i := range f {
		f[i] = make([]int, n+1)
		for j := range f[i] {
			f[i][j] = -math.MaxInt32 / 2
		}
	}
	f[0][0] = 0

	s := make([]int, m)
	for _, i := range targetIndices {
		s[i] = 1
	}

	for i := 1; i <= m; i++ {
		for j := 0; j <= n; j++ {
			f[i][j] = f[i-1][j] + s[i-1]
			if j > 0 && source[i-1] == pattern[j-1] {
				f[i][j] = max(f[i][j], f[i-1][j-1])
			}
		}
	}

	return f[m][n]
}
```

#### TypeScript

```ts
function maxRemovals(source: string, pattern: string, targetIndices: number[]): number {
    const m = source.length;
    const n = pattern.length;
    const f: number[][] = Array.from({ length: m + 1 }, () => Array(n + 1).fill(-Infinity));
    f[0][0] = 0;

    const s = Array(m).fill(0);
    for (const i of targetIndices) {
        s[i] = 1;
    }

    for (let i = 1; i <= m; i++) {
        for (let j = 0; j <= n; j++) {
            f[i][j] = f[i - 1][j] + s[i - 1];
            if (j > 0 && source[i - 1] === pattern[j - 1]) {
                f[i][j] = Math.max(f[i][j], f[i - 1][j - 1]);
            }
        }
    }

    return f[m][n];
}
```

