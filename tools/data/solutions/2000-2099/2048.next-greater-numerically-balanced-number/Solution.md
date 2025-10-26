# Solution: Enumeration

We note that the range of $n$ in the problem is $[0, 10^6]$, and one of the balanced numbers greater than $10^6$ is $1224444$. Therefore, we directly enumerate $x \in [n + 1, ..]$ and then judge whether $x$ is a balanced number. The enumerated $x$ will definitely not exceed $1224444$.

The time complexity is $O(M - n)$, where $M = 1224444$. The space complexity is $O(1)$.

## Code


#### Python3

```python
class Solution:
    def nextBeautifulNumber(self, n: int) -> int:
        for x in count(n + 1):
            y = x
            cnt = [0] * 10
            while y:
                y, v = divmod(y, 10)
                cnt[v] += 1
            if all(v == 0 or i == v for i, v in enumerate(cnt)):
                return x
```

#### Java

```java
class Solution {
    public int nextBeautifulNumber(int n) {
        for (int x = n + 1;; ++x) {
            int[] cnt = new int[10];
            for (int y = x; y > 0; y /= 10) {
                ++cnt[y % 10];
            }
            boolean ok = true;
            for (int y = x; y > 0; y /= 10) {
                if (y % 10 != cnt[y % 10]) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                return x;
            }
        }
    }
}
```

#### C++

```cpp
class Solution {
public:
    int nextBeautifulNumber(int n) {
        for (int x = n + 1;; ++x) {
            int cnt[10]{};
            for (int y = x; y > 0; y /= 10) {
                ++cnt[y % 10];
            }
            bool ok = true;
            for (int y = x; y > 0; y /= 10) {
                if (y % 10 != cnt[y % 10]) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                return x;
            }
        }
    }
};
```

#### Go

```go
func nextBeautifulNumber(n int) int {
	for x := n + 1; ; x++ {
		cnt := [10]int{}
		for y := x; y > 0; y /= 10 {
			cnt[y%10]++
		}
		ok := true
		for y := x; y > 0; y /= 10 {
			if y%10 != cnt[y%10] {
				ok = false
				break
			}
		}
		if ok {
			return x
		}
	}
}
```

#### TypeScript

```ts
function nextBeautifulNumber(n: number): number {
    for (let x = n + 1; ; ++x) {
        const cnt: number[] = Array(10).fill(0);
        for (let y = x; y > 0; y = (y / 10) | 0) {
            cnt[y % 10]++;
        }
        let ok = true;
        for (let i = 0; i < 10; ++i) {
            if (cnt[i] && cnt[i] !== i) {
                ok = false;
                break;
            }
        }
        if (ok) {
            return x;
        }
    }
}
```


## Contact information

If you have any questions or suggestions, please feel free to contact me at https://trinhminhtriet.com/
- Website: [trinhminhtriet.com](https://trinhminhtriet.com/)
- Github: [trinhminhtriet](https://github.com/trinhminhtriet)
- LinkedIn: [trinhminhtriet](https://www.linkedin.com/in/triet-trinh/)
- Twitter: [trinhminhtriet](https://twitter.com/trinhminhtriet)
- Email: [contact@trinhminhtriet.com](mailto:contact@trinhminhtriet.com)
