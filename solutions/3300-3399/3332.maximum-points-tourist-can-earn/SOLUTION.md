### Solution 1
    
# Code    


#### Python3

```python
class Solution:
    def maxScore(
        self, n: int, k: int, stayScore: List[List[int]], travelScore: List[List[int]]
    ) -> int:
        f = [[-inf] * n for _ in range(k + 1)]
        f[0] = [0] * n
        for i in range(1, k + 1):
            for j in range(n):
                for h in range(n):
                    f[i][j] = max(
                        f[i][j],
                        f[i - 1][h]
                        + (stayScore[i - 1][j] if j == h else travelScore[h][j]),
                    )
        return max(f[k])
```

#### Java

```java
class Solution {
    public int maxScore(int n, int k, int[][] stayScore, int[][] travelScore) {
        int[][] f = new int[k + 1][n];
        for (var g : f) {
            Arrays.fill(g, Integer.MIN_VALUE);
        }
        Arrays.fill(f[0], 0);
        for (int i = 1; i <= k; ++i) {
            for (int j = 0; j < n; ++j) {
                for (int h = 0; h < n; ++h) {
                    f[i][j] = Math.max(
                        f[i][j], f[i - 1][h] + (j == h ? stayScore[i - 1][j] : travelScore[h][j]));
                }
            }
        }
        return Arrays.stream(f[k]).max().getAsInt();
    }
}
```

#### C++

```cpp
class Solution {
public:
    int maxScore(int n, int k, vector<vector<int>>& stayScore, vector<vector<int>>& travelScore) {
        int f[k + 1][n];
        memset(f, 0xc0, sizeof(f));
        memset(f[0], 0, sizeof(f[0]));
        for (int i = 1; i <= k; ++i) {
            for (int j = 0; j < n; ++j) {
                for (int h = 0; h < n; ++h) {
                    f[i][j] = max(f[i][j], f[i - 1][h] + (j == h ? stayScore[i - 1][j] : travelScore[h][j]));
                }
            }
        }
        return *max_element(f[k], f[k] + n);
    }
};
```

#### Go

```go
func maxScore(n int, k int, stayScore [][]int, travelScore [][]int) (ans int) {
	f := make([][]int, k+1)
	for i := range f {
		f[i] = make([]int, n)
		for j := range f[i] {
			f[i][j] = math.MinInt32
		}
	}
	for j := 0; j < n; j++ {
		f[0][j] = 0
	}
	for i := 1; i <= k; i++ {
		for j := 0; j < n; j++ {
			f[i][j] = f[i-1][j] + stayScore[i-1][j]
			for h := 0; h < n; h++ {
				if h != j {
					f[i][j] = max(f[i][j], f[i-1][h]+travelScore[h][j])
				}
			}
		}
	}
	for j := 0; j < n; j++ {
		ans = max(ans, f[k][j])
	}
	return
}
```

#### TypeScript

```ts
function maxScore(n: number, k: number, stayScore: number[][], travelScore: number[][]): number {
    const f: number[][] = Array.from({ length: k + 1 }, () => Array(n).fill(-Infinity));
    f[0].fill(0);
    for (let i = 1; i <= k; ++i) {
        for (let j = 0; j < n; ++j) {
            for (let h = 0; h < n; ++h) {
                f[i][j] = Math.max(
                    f[i][j],
                    f[i - 1][h] + (j == h ? stayScore[i - 1][j] : travelScore[h][j]),
                );
            }
        }
    }
    return Math.max(...f[k]);
}
```

