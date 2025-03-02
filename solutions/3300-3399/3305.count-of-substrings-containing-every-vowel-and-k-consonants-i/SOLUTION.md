# Solution: Problem Transformation + Sliding Window

We can transform the problem into solving the following two subproblems:

1. Find the total number of substrings where each vowel appears at least once and contains at least $k$ consonants, denoted as $\textit{f}(k)$;
2. Find the total number of substrings where each vowel appears at least once and contains at least $k + 1$ consonants, denoted as $\textit{f}(k + 1)$.

Then the answer is $\textit{f}(k) - \textit{f}(k + 1)$.

Therefore, we design a function $\textit{f}(k)$ to count the total number of substrings where each vowel appears at least once and contains at least $k$ consonants.

We can use a hash table $\textit{cnt}$ to count the occurrences of each vowel, a variable $\textit{ans}$ to store the answer, a variable $\textit{l}$ to record the left boundary of the sliding window, and a variable $\textit{x}$ to record the number of consonants in the current window.

Traverse the string. If the current character is a vowel, add it to the hash table $\textit{cnt}$; otherwise, increment $\textit{x}$ by one. If $\textit{x} \ge k$ and the size of the hash table $\textit{cnt}$ is $5$, it means the current window meets the conditions. We then move the left boundary in a loop until the window no longer meets the conditions. At this point, all substrings ending at the right boundary $\textit{r}$ and with the left boundary in the range $[0, .. \textit{l} - 1]$ meet the conditions, totaling $\textit{l}$ substrings. We add $\textit{l}$ to the answer. Continue traversing the string until the end, and we get $\textit{f}(k)$.

Finally, we return $\textit{f}(k) - \textit{f}(k + 1)$.

The time complexity is $O(n)$, where $n$ is the length of the string $\textit{word}$. The space complexity is $O(1)$.
    
# Code    


#### Python3

```python
class Solution:
    def countOfSubstrings(self, word: str, k: int) -> int:
        def f(k: int) -> int:
            cnt = Counter()
            ans = l = x = 0
            for c in word:
                if c in "aeiou":
                    cnt[c] += 1
                else:
                    x += 1
                while x >= k and len(cnt) == 5:
                    d = word[l]
                    if d in "aeiou":
                        cnt[d] -= 1
                        if cnt[d] == 0:
                            cnt.pop(d)
                    else:
                        x -= 1
                    l += 1
                ans += l
            return ans

        return f(k) - f(k + 1)
```

#### Java

```java
class Solution {
    public int countOfSubstrings(String word, int k) {
        return f(word, k) - f(word, k + 1);
    }

    private int f(String word, int k) {
        int ans = 0;
        int l = 0, x = 0;
        Map<Character, Integer> cnt = new HashMap<>(5);
        for (char c : word.toCharArray()) {
            if (vowel(c)) {
                cnt.merge(c, 1, Integer::sum);
            } else {
                ++x;
            }
            while (x >= k && cnt.size() == 5) {
                char d = word.charAt(l++);
                if (vowel(d)) {
                    if (cnt.merge(d, -1, Integer::sum) == 0) {
                        cnt.remove(d);
                    }
                } else {
                    --x;
                }
            }
            ans += l;
        }
        return ans;
    }

    private boolean vowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }
}
```

#### C++

```cpp
class Solution {
public:
    int countOfSubstrings(string word, int k) {
        auto f = [&](int k) -> int {
            int ans = 0;
            int l = 0, x = 0;
            unordered_map<char, int> cnt;
            auto vowel = [&](char c) -> bool {
                return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
            };
            for (char c : word) {
                if (vowel(c)) {
                    cnt[c]++;
                } else {
                    ++x;
                }
                while (x >= k && cnt.size() == 5) {
                    char d = word[l++];
                    if (vowel(d)) {
                        if (--cnt[d] == 0) {
                            cnt.erase(d);
                        }
                    } else {
                        --x;
                    }
                }
                ans += l;
            }
            return ans;
        };

        return f(k) - f(k + 1);
    }
};
```

#### Go

```go
func countOfSubstrings(word string, k int) int {
	f := func(k int) int {
		var ans int = 0
		l, x := 0, 0
		cnt := make(map[rune]int)
		vowel := func(c rune) bool {
			return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'
		}
		for _, c := range word {
			if vowel(c) {
				cnt[c]++
			} else {
				x++
			}
			for x >= k && len(cnt) == 5 {
				d := rune(word[l])
				l++
				if vowel(d) {
					cnt[d]--
					if cnt[d] == 0 {
						delete(cnt, d)
					}
				} else {
					x--
				}
			}
			ans += l
		}
		return ans
	}

	return f(k) - f(k+1)
}
```

#### TypeScript

```ts
function countOfSubstrings(word: string, k: number): number {
    const f = (k: number): number => {
        let ans = 0;
        let l = 0,
            x = 0;
        const cnt = new Map<string, number>();

        const vowel = (c: string): boolean => {
            return c === 'a' || c === 'e' || c === 'i' || c === 'o' || c === 'u';
        };

        for (const c of word) {
            if (vowel(c)) {
                cnt.set(c, (cnt.get(c) || 0) + 1);
            } else {
                x++;
            }

            while (x >= k && cnt.size === 5) {
                const d = word[l++];
                if (vowel(d)) {
                    cnt.set(d, cnt.get(d)! - 1);
                    if (cnt.get(d) === 0) {
                        cnt.delete(d);
                    }
                } else {
                    x--;
                }
            }
            ans += l;
        }

        return ans;
    };

    return f(k) - f(k + 1);
}
```

