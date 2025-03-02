### Solution 1
    
# Code    


#### Python3

```python
class Solution:
    def possibleStringCount(self, word: str) -> int:
        return 1 + sum(x == y for x, y in pairwise(word))
```

#### Java

```java
class Solution {
    public int possibleStringCount(String word) {
        int f = 1;
        for (int i = 1; i < word.length(); ++i) {
            if (word.charAt(i) == word.charAt(i - 1)) {
                ++f;
            }
        }
        return f;
    }
}
```

#### C++

```cpp
class Solution {
public:
    int possibleStringCount(string word) {
        int f = 1;
        for (int i = 1; i < word.size(); ++i) {
            f += word[i] == word[i - 1];
        }
        return f;
    }
};
```

#### Go

```go
func possibleStringCount(word string) int {
	f := 1
	for i := 1; i < len(word); i++ {
		if word[i] == word[i-1] {
			f++
		}
	}
	return f
}
```

#### TypeScript

```ts
function possibleStringCount(word: string): number {
    let f = 1;
    for (let i = 1; i < word.length; ++i) {
        f += word[i] === word[i - 1] ? 1 : 0;
    }
    return f;
}
```

