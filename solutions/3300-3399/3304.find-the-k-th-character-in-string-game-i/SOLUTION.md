# Solution: Simulation

We can use an array $\textit{word}$ to store the string after each operation. When the length of $\textit{word}$ is less than $k$, we continuously perform operations on $\textit{word}$.

Finally, return $\textit{word}[k - 1]$.

The time complexity is $O(k)$, and the space complexity is $O(k)$. Here, $k$ is the input parameter.
    
# Code    


#### Python3

```python
class Solution:
    def kthCharacter(self, k: int) -> str:
        word = [0]
        while len(word) < k:
            word.extend([(x + 1) % 26 for x in word])
        return chr(ord("a") + word[k - 1])
```

#### Java

```java
class Solution {
    public char kthCharacter(int k) {
        List<Integer> word = new ArrayList<>();
        word.add(0);
        while (word.size() < k) {
            for (int i = 0, m = word.size(); i < m; ++i) {
                word.add((word.get(i) + 1) % 26);
            }
        }
        return (char) ('a' + word.get(k - 1));
    }
}
```

#### C++

```cpp
class Solution {
public:
    char kthCharacter(int k) {
        vector<int> word;
        word.push_back(0);
        while (word.size() < k) {
            int m = word.size();
            for (int i = 0; i < m; ++i) {
                word.push_back((word[i] + 1) % 26);
            }
        }
        return 'a' + word[k - 1];
    }
};
```

#### Go

```go
func kthCharacter(k int) byte {
	word := []int{0}
	for len(word) < k {
		m := len(word)
		for i := 0; i < m; i++ {
			word = append(word, (word[i]+1)%26)
		}
	}
	return 'a' + byte(word[k-1])
}
```

#### TypeScript

```ts
function kthCharacter(k: number): string {
    const word: number[] = [0];
    while (word.length < k) {
        word.push(...word.map(x => (x + 1) % 26));
    }
    return String.fromCharCode(97 + word[k - 1]);
}
```

