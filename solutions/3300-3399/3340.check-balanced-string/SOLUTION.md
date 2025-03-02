# Solution: Simulation

We can use an array $f$ of length $2$ to record the sum of numbers at even indices and odd indices. Then, we traverse the string $\textit{nums}$ and add the numbers to the corresponding positions based on the parity of the indices. Finally, we check whether $f[0]$ is equal to $f[1]$.

The time complexity is $O(n)$, where $n$ is the length of the string $\textit{nums}$. The space complexity is $O(1)$.
    
# Code    


#### Python3

```python
class Solution:
    def isBalanced(self, num: str) -> bool:
        f = [0, 0]
        for i, x in enumerate(map(int, num)):
            f[i & 1] += x
        return f[0] == f[1]
```

#### Java

```java
class Solution {
    public boolean isBalanced(String num) {
        int[] f = new int[2];
        for (int i = 0; i < num.length(); ++i) {
            f[i & 1] += num.charAt(i) - '0';
        }
        return f[0] == f[1];
    }
}
```

#### C++

```cpp
class Solution {
public:
    bool isBalanced(string num) {
        int f[2]{};
        for (int i = 0; i < num.size(); ++i) {
            f[i & 1] += num[i] - '0';
        }
        return f[0] == f[1];
    }
};
```

#### Go

```go
func isBalanced(num string) bool {
	f := [2]int{}
	for i, c := range num {
		f[i&1] += int(c - '0')
	}
	return f[0] == f[1]
}
```

#### TypeScript

```ts
function isBalanced(num: string): boolean {
    const f = [0, 0];
    for (let i = 0; i < num.length; ++i) {
        f[i & 1] += +num[i];
    }
    return f[0] === f[1];
}
```

