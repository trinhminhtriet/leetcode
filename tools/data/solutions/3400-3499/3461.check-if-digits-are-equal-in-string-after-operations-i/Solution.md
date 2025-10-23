# Solution: Simulation

We can simulate the operations described in the problem until the string $s$ contains exactly two digits, and then check if these two digits are the same.

The time complexity is $O(n^2)$, and the space complexity is $O(n)$. Here, $n$ is the length of the string $s$.

## Code


#### Python3

```python
class Solution:
    def hasSameDigits(self, s: str) -> bool:
        t = list(map(int, s))
        n = len(t)
        for k in range(n - 1, 1, -1):
            for i in range(k):
                t[i] = (t[i] + t[i + 1]) % 10
        return t[0] == t[1]
```

#### Java

```java
class Solution {
    public boolean hasSameDigits(String s) {
        char[] t = s.toCharArray();
        int n = t.length;
        for (int k = n - 1; k > 1; --k) {
            for (int i = 0; i < k; ++i) {
                t[i] = (char) ((t[i] - '0' + t[i + 1] - '0') % 10 + '0');
            }
        }
        return t[0] == t[1];
    }
}
```

#### C++

```cpp
class Solution {
public:
    bool hasSameDigits(string s) {
        int n = s.size();
        string t = s;
        for (int k = n - 1; k > 1; --k) {
            for (int i = 0; i < k; ++i) {
                t[i] = (t[i] - '0' + t[i + 1] - '0') % 10 + '0';
            }
        }
        return t[0] == t[1];
    }
};
```

#### Go

```go
func hasSameDigits(s string) bool {
	t := []byte(s)
	n := len(t)
	for k := n - 1; k > 1; k-- {
		for i := 0; i < k; i++ {
			t[i] = (t[i]-'0'+t[i+1]-'0')%10 + '0'
		}
	}
	return t[0] == t[1]
}
```

#### TypeScript

```ts
function hasSameDigits(s: string): boolean {
    const t = s.split('').map(Number);
    const n = t.length;
    for (let k = n - 1; k > 1; --k) {
        for (let i = 0; i < k; ++i) {
            t[i] = (t[i] + t[i + 1]) % 10;
        }
    }
    return t[0] === t[1];
}
```


## Contact information

If you have any questions or suggestions, please feel free to contact me at https://trinhminhtriet.com/
- Website: [trinhminhtriet.com](https://trinhminhtriet.com/)
- Github: [trinhminhtriet](https://github.com/trinhminhtriet)
- LinkedIn: [trinhminhtriet](https://www.linkedin.com/in/triet-trinh/)
- Twitter: [trinhminhtriet](https://twitter.com/trinhminhtriet)
- Email: [contact@trinhminhtriet.com](mailto:contact@trinhminhtriet.com)
