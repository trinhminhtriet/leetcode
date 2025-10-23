# Solution: Counting

We traverse the array $\textit{operations}$. For each operation $\textit{operations}[i]$, if it contains `'+'`, we increment the answer by $1$, otherwise, we decrement the answer by $1$.

The time complexity is $O(n)$, where $n$ is the length of the array $\textit{operations}$. The space complexity is $O(1)$.

## Code


#### Python3

```python
class Solution:
    def finalValueAfterOperations(self, operations: List[str]) -> int:
        return sum(1 if s[1] == '+' else -1 for s in operations)
```

#### Java

```java
class Solution {
    public int finalValueAfterOperations(String[] operations) {
        int ans = 0;
        for (var s : operations) {
            ans += (s.charAt(1) == '+' ? 1 : -1);
        }
        return ans;
    }
}
```

#### C++

```cpp
class Solution {
public:
    int finalValueAfterOperations(vector<string>& operations) {
        int ans = 0;
        for (auto& s : operations) {
            ans += s[1] == '+' ? 1 : -1;
        }
        return ans;
    }
};
```

#### Go

```go
func finalValueAfterOperations(operations []string) (ans int) {
	for _, s := range operations {
		if s[1] == '+' {
			ans += 1
		} else {
			ans -= 1
		}
	}
	return
}
```

#### TypeScript

```ts
function finalValueAfterOperations(operations: string[]): number {
    return operations.reduce((acc, op) => acc + (op[1] === '+' ? 1 : -1), 0);
}
```

#### Rust

```rust
impl Solution {
    pub fn final_value_after_operations(operations: Vec<String>) -> i32 {
        let mut ans = 0;
        for s in operations.iter() {
            ans += if s.as_bytes()[1] == b'+' { 1 } else { -1 };
        }
        ans
    }
}
```

#### JavaScript

```js
/**
 * @param {string[]} operations
 * @return {number}
 */
var finalValueAfterOperations = function (operations) {
    return operations.reduce((acc, op) => acc + (op[1] === '+' ? 1 : -1), 0);
};
```

#### C

```c
int finalValueAfterOperations(char** operations, int operationsSize) {
    int ans = 0;
    for (int i = 0; i < operationsSize; i++) {
        ans += operations[i][1] == '+' ? 1 : -1;
    }
    return ans;
}
```


## Contact information

If you have any questions or suggestions, please feel free to contact me at https://trinhminhtriet.com/
- Website: [trinhminhtriet.com](https://trinhminhtriet.com/)
- Github: [trinhminhtriet](https://github.com/trinhminhtriet)
- LinkedIn: [trinhminhtriet](https://www.linkedin.com/in/triet-trinh/)
- Twitter: [trinhminhtriet](https://twitter.com/trinhminhtriet)
- Email: [contact@trinhminhtriet.com](mailto:contact@trinhminhtriet.com)
