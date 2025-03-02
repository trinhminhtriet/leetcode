### Solution 1
    
# Code    


#### Python3

```python
class Solution:
    def constructGridLayout(self, n: int, edges: List[List[int]]) -> List[List[int]]:
        g = [[] for _ in range(n)]
        for u, v in edges:
            g[u].append(v)
            g[v].append(u)
        deg = [-1] * 5
        for x, ys in enumerate(g):
            deg[len(ys)] = x
        if deg[1] != -1:
            row = [deg[1]]
        elif deg[4] == -1:
            x = deg[2]
            for y in g[x]:
                if len(g[y]) == 2:
                    row = [x, y]
                    break
        else:
            x = deg[2]
            row = [x]
            pre = x
            x = g[x][0]
            while len(g[x]) > 2:
                row.append(x)
                for y in g[x]:
                    if y != pre and len(g[y]) < 4:
                        pre = x
                        x = y
                        break
            row.append(x)

        ans = [row]
        vis = [False] * n
        for _ in range(n // len(row) - 1):
            for x in row:
                vis[x] = True
            nxt = []
            for x in row:
                for y in g[x]:
                    if not vis[y]:
                        nxt.append(y)
                        break
            ans.append(nxt)
            row = nxt
        return ans
```

#### Java

```java
class Solution {
    public int[][] constructGridLayout(int n, int[][] edges) {
        List<Integer>[] g = new List[n];
        Arrays.setAll(g, k -> new ArrayList<>());
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            g[u].add(v);
            g[v].add(u);
        }

        int[] deg = new int[5];
        Arrays.fill(deg, -1);

        for (int x = 0; x < n; x++) {
            deg[g[x].size()] = x;
        }

        List<Integer> row = new ArrayList<>();
        if (deg[1] != -1) {
            row.add(deg[1]);
        } else if (deg[4] == -1) {
            int x = deg[2];
            for (int y : g[x]) {
                if (g[y].size() == 2) {
                    row.add(x);
                    row.add(y);
                    break;
                }
            }
        } else {
            int x = deg[2];
            row.add(x);
            int pre = x;
            x = g[x].get(0);
            while (g[x].size() > 2) {
                row.add(x);
                for (int y : g[x]) {
                    if (y != pre && g[y].size() < 4) {
                        pre = x;
                        x = y;
                        break;
                    }
                }
            }
            row.add(x);
        }

        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>(row));

        boolean[] vis = new boolean[n];
        int rowSize = row.size();
        for (int i = 0; i < n / rowSize - 1; i++) {
            for (int x : row) {
                vis[x] = true;
            }
            List<Integer> nxt = new ArrayList<>();
            for (int x : row) {
                for (int y : g[x]) {
                    if (!vis[y]) {
                        nxt.add(y);
                        break;
                    }
                }
            }
            res.add(new ArrayList<>(nxt));
            row = nxt;
        }

        int[][] ans = new int[res.size()][rowSize];
        for (int i = 0; i < res.size(); i++) {
            for (int j = 0; j < rowSize; j++) {
                ans[i][j] = res.get(i).get(j);
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
    vector<vector<int>> constructGridLayout(int n, vector<vector<int>>& edges) {
        vector<vector<int>> g(n);
        for (auto& e : edges) {
            int u = e[0], v = e[1];
            g[u].push_back(v);
            g[v].push_back(u);
        }

        vector<int> deg(5, -1);
        for (int x = 0; x < n; ++x) {
            deg[g[x].size()] = x;
        }

        vector<int> row;
        if (deg[1] != -1) {
            row.push_back(deg[1]);
        } else if (deg[4] == -1) {
            int x = deg[2];
            for (int y : g[x]) {
                if (g[y].size() == 2) {
                    row.push_back(x);
                    row.push_back(y);
                    break;
                }
            }
        } else {
            int x = deg[2];
            row.push_back(x);
            int pre = x;
            x = g[x][0];
            while (g[x].size() > 2) {
                row.push_back(x);
                for (int y : g[x]) {
                    if (y != pre && g[y].size() < 4) {
                        pre = x;
                        x = y;
                        break;
                    }
                }
            }
            row.push_back(x);
        }

        vector<vector<int>> ans;
        ans.push_back(row);
        vector<bool> vis(n, false);
        int rowSize = row.size();
        for (int i = 0; i < n / rowSize - 1; ++i) {
            for (int x : row) {
                vis[x] = true;
            }
            vector<int> nxt;
            for (int x : row) {
                for (int y : g[x]) {
                    if (!vis[y]) {
                        nxt.push_back(y);
                        break;
                    }
                }
            }
            ans.push_back(nxt);
            row = nxt;
        }

        return ans;
    }
};
```

#### Go

```go
func constructGridLayout(n int, edges [][]int) [][]int {
	g := make([][]int, n)
	for _, e := range edges {
		u, v := e[0], e[1]
		g[u] = append(g[u], v)
		g[v] = append(g[v], u)
	}

	deg := make([]int, 5)
	for i := range deg {
		deg[i] = -1
	}

	for x := 0; x < n; x++ {
		deg[len(g[x])] = x
	}

	var row []int
	if deg[1] != -1 {
		row = append(row, deg[1])
	} else if deg[4] == -1 {
		x := deg[2]
		for _, y := range g[x] {
			if len(g[y]) == 2 {
				row = append(row, x, y)
				break
			}
		}
	} else {
		x := deg[2]
		row = append(row, x)
		pre := x
		x = g[x][0]
		for len(g[x]) > 2 {
			row = append(row, x)
			for _, y := range g[x] {
				if y != pre && len(g[y]) < 4 {
					pre = x
					x = y
					break
				}
			}
		}
		row = append(row, x)
	}

	ans := [][]int{row}
	vis := make([]bool, n)
	rowSize := len(row)
	for i := 0; i < n/rowSize-1; i++ {
		for _, x := range row {
			vis[x] = true
		}
		nxt := []int{}
		for _, x := range row {
			for _, y := range g[x] {
				if !vis[y] {
					nxt = append(nxt, y)
					break
				}
			}
		}
		ans = append(ans, nxt)
		row = nxt
	}

	return ans
}
```

#### TypeScript

```ts
function constructGridLayout(n: number, edges: number[][]): number[][] {
    const g: number[][] = Array.from({ length: n }, () => []);
    for (const [u, v] of edges) {
        g[u].push(v);
        g[v].push(u);
    }

    const deg: number[] = Array(5).fill(-1);
    for (let x = 0; x < n; x++) {
        deg[g[x].length] = x;
    }

    let row: number[] = [];
    if (deg[1] !== -1) {
        row.push(deg[1]);
    } else if (deg[4] === -1) {
        let x = deg[2];
        for (const y of g[x]) {
            if (g[y].length === 2) {
                row.push(x, y);
                break;
            }
        }
    } else {
        let x = deg[2];
        row.push(x);
        let pre = x;
        x = g[x][0];
        while (g[x].length > 2) {
            row.push(x);
            for (const y of g[x]) {
                if (y !== pre && g[y].length < 4) {
                    pre = x;
                    x = y;
                    break;
                }
            }
        }
        row.push(x);
    }

    const ans: number[][] = [row];
    const vis: boolean[] = Array(n).fill(false);
    const rowSize = row.length;

    for (let i = 0; i < Math.floor(n / rowSize) - 1; i++) {
        for (const x of row) {
            vis[x] = true;
        }
        const nxt: number[] = [];
        for (const x of row) {
            for (const y of g[x]) {
                if (!vis[y]) {
                    nxt.push(y);
                    break;
                }
            }
        }
        ans.push(nxt);
        row = nxt;
    }

    return ans;
}
```

