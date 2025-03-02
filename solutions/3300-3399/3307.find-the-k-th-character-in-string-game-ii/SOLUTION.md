# Solution: Recurrence

Since the length of the string doubles after each operation, if we perform $i$ operations, the length of the string will be $2^i$.

We can simulate this process to find the first string length $n$ that is greater than or equal to $k$.

Next, we backtrack and discuss the following cases:

-   If $k \gt n / 2$, it means $k$ is in the second half. If $\textit{operations}[i - 1] = 1$, it means the character at position $k$ is obtained by adding $1$ to the character in the first half. We add $1$ to it. Then we update $k$ to $k - n / 2$.
-   If $k \le n / 2$, it means $k$ is in the first half and is not affected by $\textit{operations}[i - 1]$.
-   Next, we update $n$ to $n / 2$ and continue backtracking until $n = 1$.

Finally, we take the resulting number modulo $26$ and add the ASCII code of `'a'` to get the answer.

The time complexity is $O(\log k)$, and the space complexity is $O(1)$.
    
# Code    


#### Python3

```python
class Solution:
    def kthCharacter(self, k: int, operations: List[int]) -> str:
        n, i = 1, 0
        while n < k:
            n *= 2
            i += 1
        d = 0
        while n > 1:
            if k > n // 2:
                k -= n // 2
                d += operations[i - 1]
            n //= 2
            i -= 1
        return chr(d % 26 + ord("a"))
```

#### Java

```java
class Solution {
    public char kthCharacter(long k, int[] operations) {
        long n = 1;
        int i = 0;
        while (n < k) {
            n *= 2;
            ++i;
        }
        int d = 0;
        while (n > 1) {
            if (k > n / 2) {
                k -= n / 2;
                d += operations[i - 1];
            }
            n /= 2;
            --i;
        }
        return (char) ('a' + (d % 26));
    }
}
```

#### C++

```cpp
class Solution {
public:
    char kthCharacter(long long k, vector<int>& operations) {
        long long n = 1;
        int i = 0;
        while (n < k) {
            n *= 2;
            ++i;
        }
        int d = 0;
        while (n > 1) {
            if (k > n / 2) {
                k -= n / 2;
                d += operations[i - 1];
            }
            n /= 2;
            --i;
        }
        return 'a' + (d % 26);
    }
};
```

#### Go

```go
func kthCharacter(k int64, operations []int) byte {
	n := int64(1)
	i := 0
	for n < k {
		n *= 2
		i++
	}
	d := 0
	for n > 1 {
		if k > n/2 {
			k -= n / 2
			d += operations[i-1]
		}
		n /= 2
		i--
	}
	return byte('a' + (d % 26))
}
```

#### TypeScript

```ts
function kthCharacter(k: number, operations: number[]): string {
    let n = 1;
    let i = 0;
    while (n < k) {
        n *= 2;
        i++;
    }
    let d = 0;
    while (n > 1) {
        if (k > n / 2) {
            k -= n / 2;
            d += operations[i - 1];
        }
        n /= 2;
        i--;
    }
    return String.fromCharCode('a'.charCodeAt(0) + (d % 26));
}
```

