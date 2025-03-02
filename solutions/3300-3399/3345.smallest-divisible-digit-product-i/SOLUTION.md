# Solution: Enumeration

We note that within every $10$ numbers, there will definitely be an integer whose digit product is $0$. Therefore, we can directly enumerate integers greater than or equal to $n$ until we find an integer whose digit product is divisible by $t$.

The time complexity is $O(\log n)$, and the space complexity is $O(1)$.
    
# Code    


#### Python3

```python
class Solution:
    def smallestNumber(self, n: int, t: int) -> int:
        for i in count(n):
            p = 1
            x = i
            while x:
                p *= x % 10
                x //= 10
            if p % t == 0:
                return i
```

#### Java

```java
class Solution {
    public int smallestNumber(int n, int t) {
        for (int i = n;; ++i) {
            int p = 1;
            for (int x = i; x > 0; x /= 10) {
                p *= (x % 10);
            }
            if (p % t == 0) {
                return i;
            }
        }
    }
}
```

#### C++

```cpp
class Solution {
public:
    int smallestNumber(int n, int t) {
        for (int i = n;; ++i) {
            int p = 1;
            for (int x = i; x > 0; x /= 10) {
                p *= (x % 10);
            }
            if (p % t == 0) {
                return i;
            }
        }
    }
};
```

#### Go

```go
func smallestNumber(n int, t int) int {
	for i := n; ; i++ {
		p := 1
		for x := i; x > 0; x /= 10 {
			p *= x % 10
		}
		if p%t == 0 {
			return i
		}
	}
}
```

#### TypeScript

```ts
function smallestNumber(n: number, t: number): number {
    for (let i = n; ; ++i) {
        let p = 1;
        for (let x = i; x; x = Math.floor(x / 10)) {
            p *= x % 10;
        }
        if (p % t === 0) {
            return i;
        }
    }
}
```

