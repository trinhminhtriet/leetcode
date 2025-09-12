# Solution: Brain Teaser

Let's denote the number of vowels in the string as $k$.

If $k = 0$, meaning there are no vowels in the string, then Little Red cannot remove any substring, and Little Ming wins directly.

If $k$ is odd, then Little Red can remove the entire string, resulting in a direct win for Little Red.

If $k$ is even, then Little Red can remove $k - 1$ vowels, leaving one vowel in the string. In this case, Little Ming cannot remove any substring, leading to a direct win for Little Red.

In conclusion, if the string contains vowels, then Little Red wins; otherwise, Little Ming wins.

The time complexity is $O(n)$, where $n$ is the length of the string $s$. The space complexity is $O(1)$.

## Code


#### Python3

```python
class Solution:
    def doesAliceWin(self, s: str) -> bool:
        vowels = set("aeiou")
        return any(c in vowels for c in s)
```

#### Java

```java
class Solution {
    public boolean doesAliceWin(String s) {
        for (int i = 0; i < s.length(); ++i) {
            if ("aeiou".indexOf(s.charAt(i)) != -1) {
                return true;
            }
        }
        return false;
    }
}
```

#### C++

```cpp
class Solution {
public:
    bool doesAliceWin(string s) {
        string vowels = "aeiou";
        for (char c : s) {
            if (vowels.find(c) != string::npos) {
                return true;
            }
        }
        return false;
    }
};
```

#### Go

```go
func doesAliceWin(s string) bool {
	vowels := "aeiou"
	for _, c := range s {
		if strings.ContainsRune(vowels, c) {
			return true
		}
	}
	return false
}
```

#### TypeScript

```ts
function doesAliceWin(s: string): boolean {
    const vowels = 'aeiou';
    for (const c of s) {
        if (vowels.includes(c)) {
            return true;
        }
    }
    return false;
}
```


## Contact information

If you have any questions or suggestions, please feel free to contact me at https://trinhminhtriet.com/
- Website: [trinhminhtriet.com](https://trinhminhtriet.com/)
- Github: [trinhminhtriet](https://github.com/trinhminhtriet)
- LinkedIn: [trinhminhtriet](https://www.linkedin.com/in/triet-trinh/)
- Twitter: [trinhminhtriet](https://twitter.com/trinhminhtriet)
- Email: [contact@trinhminhtriet.com](mailto:contact@trinhminhtriet.com)
