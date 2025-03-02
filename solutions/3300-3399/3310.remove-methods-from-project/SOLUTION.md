# Solution: Two DFS

We can start from $k$ and find all suspicious methods, recording them in the array $\textit{suspicious}$. Then, we traverse from $0$ to $n-1$, starting from all non-suspicious methods, and mark all reachable methods as non-suspicious. Finally, we return all non-suspicious methods.

The time complexity is $O(n + m)$, and the space complexity is $O(n + m)$. Here, $n$ and $m$ represent the number of methods and the number of call relationships, respectively.
    
# Code    


#### Python3

```python
class Solution:
    def remainingMethods(
        self, n: int, k: int, invocations: List[List[int]]
    ) -> List[int]:
        def dfs(i: int):
            suspicious[i] = True
            for j in g[i]:
                if not suspicious[j]:
                    dfs(j)

        def dfs2(i: int):
            vis[i] = True
            for j in f[i]:
                if not vis[j]:
                    suspicious[j] = False
                    dfs2(j)

        f = [[] for _ in range(n)]
        g = [[] for _ in range(n)]
        for a, b in invocations:
            f[a].append(b)
            f[b].append(a)
            g[a].append(b)
        suspicious = [False] * n
        dfs(k)

        vis = [False] * n
        ans = []
        for i in range(n):
            if not suspicious[i] and not vis[i]:
                dfs2(i)
        return [i for i in range(n) if not suspicious[i]]
```

#### Java

```java
class Solution {
    private boolean[] suspicious;
    private boolean[] vis;
    private List<Integer>[] f;
    private List<Integer>[] g;

    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        suspicious = new boolean[n];
        vis = new boolean[n];
        f = new List[n];
        g = new List[n];
        Arrays.setAll(f, i -> new ArrayList<>());
        Arrays.setAll(g, i -> new ArrayList<>());
        for (var e : invocations) {
            int a = e[0], b = e[1];
            f[a].add(b);
            f[b].add(a);
            g[a].add(b);
        }
        dfs(k);
        for (int i = 0; i < n; ++i) {
            if (!suspicious[i] && !vis[i]) {
                dfs2(i);
            }
        }
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            if (!suspicious[i]) {
                ans.add(i);
            }
        }
        return ans;
    }

    private void dfs(int i) {
        suspicious[i] = true;
        for (int j : g[i]) {
            if (!suspicious[j]) {
                dfs(j);
            }
        }
    }

    private void dfs2(int i) {
        vis[i] = true;
        for (int j : f[i]) {
            if (!vis[j]) {
                suspicious[j] = false;
                dfs2(j);
            }
        }
    }
}
```

#### C++

```cpp
class Solution {
public:
    vector<int> remainingMethods(int n, int k, vector<vector<int>>& invocations) {
        vector<bool> suspicious(n);
        vector<bool> vis(n);
        vector<int> f[n];
        vector<int> g[n];
        for (const auto& e : invocations) {
            int a = e[0], b = e[1];
            f[a].push_back(b);
            f[b].push_back(a);
            g[a].push_back(b);
        }
        auto dfs = [&](auto&& dfs, int i) -> void {
            suspicious[i] = true;
            for (int j : g[i]) {
                if (!suspicious[j]) {
                    dfs(dfs, j);
                }
            }
        };
        dfs(dfs, k);
        auto dfs2 = [&](auto&& dfs2, int i) -> void {
            vis[i] = true;
            for (int j : f[i]) {
                if (!vis[j]) {
                    suspicious[j] = false;
                    dfs2(dfs2, j);
                }
            }
        };
        for (int i = 0; i < n; ++i) {
            if (!suspicious[i] && !vis[i]) {
                dfs2(dfs2, i);
            }
        }
        vector<int> ans;
        for (int i = 0; i < n; ++i) {
            if (!suspicious[i]) {
                ans.push_back(i);
            }
        }
        return ans;
    }
};
```

#### Go

```go
func remainingMethods(n int, k int, invocations [][]int) []int {
	suspicious := make([]bool, n)
	vis := make([]bool, n)
	f := make([][]int, n)
	g := make([][]int, n)

	for _, e := range invocations {
		a, b := e[0], e[1]
		f[a] = append(f[a], b)
		f[b] = append(f[b], a)
		g[a] = append(g[a], b)
	}

	var dfs func(int)
	dfs = func(i int) {
		suspicious[i] = true
		for _, j := range g[i] {
			if !suspicious[j] {
				dfs(j)
			}
		}
	}

	dfs(k)

	var dfs2 func(int)
	dfs2 = func(i int) {
		vis[i] = true
		for _, j := range f[i] {
			if !vis[j] {
				suspicious[j] = false
				dfs2(j)
			}
		}
	}

	for i := 0; i < n; i++ {
		if !suspicious[i] && !vis[i] {
			dfs2(i)
		}
	}

	var ans []int
	for i := 0; i < n; i++ {
		if !suspicious[i] {
			ans = append(ans, i)
		}
	}

	return ans
}
```

#### TypeScript

```ts
function remainingMethods(n: number, k: number, invocations: number[][]): number[] {
    const suspicious: boolean[] = Array(n).fill(false);
    const vis: boolean[] = Array(n).fill(false);
    const f: number[][] = Array.from({ length: n }, () => []);
    const g: number[][] = Array.from({ length: n }, () => []);

    for (const [a, b] of invocations) {
        f[a].push(b);
        f[b].push(a);
        g[a].push(b);
    }

    const dfs = (i: number) => {
        suspicious[i] = true;
        for (const j of g[i]) {
            if (!suspicious[j]) {
                dfs(j);
            }
        }
    };

    dfs(k);

    const dfs2 = (i: number) => {
        vis[i] = true;
        for (const j of f[i]) {
            if (!vis[j]) {
                suspicious[j] = false;
                dfs2(j);
            }
        }
    };

    for (let i = 0; i < n; i++) {
        if (!suspicious[i] && !vis[i]) {
            dfs2(i);
        }
    }

    return Array.from({ length: n }, (_, i) => i).filter(i => !suspicious[i]);
}
```

