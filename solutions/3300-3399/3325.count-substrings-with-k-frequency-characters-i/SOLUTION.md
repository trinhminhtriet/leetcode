# Solution: Sliding Window

We can enumerate the right endpoint of the substring, and then use a sliding window to maintain the left endpoint of the substring, ensuring that the occurrence count of each character in the sliding window is less than $k$.

We can use an array $\textit{cnt}$ to maintain the occurrence count of each character in the sliding window, then use a variable $\textit{l}$ to maintain the left endpoint of the sliding window, and use a variable $\textit{ans}$ to maintain the answer.

When we enumerate the right endpoint, we can add the character at the right endpoint to the sliding window, then check if the occurrence count of the character at the right endpoint in the sliding window is greater than or equal to $k$. If it is, we remove the character at the left endpoint from the sliding window until the occurrence count of each character in the sliding window is less than $k$. At this point, for substrings with left endpoints in the range $[0, ..l - 1]$ and right endpoint $r$, all satisfy the problem's requirements, so we add $l$ to the answer.

After enumeration, we return the answer.

The time complexity is $O(n)$, where $n$ is the length of the string $s$. The space complexity is $O(|\Sigma|)$, where $\Sigma$ is the character set, which in this case is the set of lowercase letters, so $|\Sigma| = 26$.
    
# Code    


#### Python3

```python
class Solution:
    def numberOfSubstrings(self, s: str, k: int) -> int:
        cnt = Counter()
        ans = l = 0
        for c in s:
            cnt[c] += 1
            while cnt[c] >= k:
                cnt[s[l]] -= 1
                l += 1
            ans += l
        return ans
```

#### Java

```java
class Solution {
    public int numberOfSubstrings(String s, int k) {
        int[] cnt = new int[26];
        int ans = 0, l = 0;
        for (int r = 0; r < s.length(); ++r) {
            int c = s.charAt(r) - 'a';
            ++cnt[c];
            while (cnt[c] >= k) {
                --cnt[s.charAt(l) - 'a'];
                l++;
            }
            ans += l;
        }
        return ans;
    }
}
```

#### C++

```cpp
class Solution {
public:
    int numberOfSubstrings(string s, int k) {
        int n = s.size();
        int ans = 0, l = 0;
        int cnt[26]{};
        for (char& c : s) {
            ++cnt[c - 'a'];
            while (cnt[c - 'a'] >= k) {
                --cnt[s[l++] - 'a'];
            }
            ans += l;
        }
        return ans;
    }
};
```

#### Go

```go
func numberOfSubstrings(s string, k int) (ans int) {
	l := 0
	cnt := [26]int{}
	for _, c := range s {
		cnt[c-'a']++
		for cnt[c-'a'] >= k {
			cnt[s[l]-'a']--
			l++
		}
		ans += l
	}
	return
}
```

#### TypeScript

```ts
function numberOfSubstrings(s: string, k: number): number {
    let [ans, l] = [0, 0];
    const cnt: number[] = Array(26).fill(0);
    for (const c of s) {
        const x = c.charCodeAt(0) - 'a'.charCodeAt(0);
        ++cnt[x];
        while (cnt[x] >= k) {
            --cnt[s[l++].charCodeAt(0) - 'a'.charCodeAt(0)];
        }
        ans += l;
    }
    return ans;
}
```

