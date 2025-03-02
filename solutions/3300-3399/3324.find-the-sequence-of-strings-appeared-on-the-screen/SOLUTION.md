# Solution: Simulation

We can simulate Alice's typing process, starting from an empty string and updating the string after each keystroke until the target string is obtained.

The time complexity is $O(n^2 \times |\Sigma|)$, where $n$ is the length of the target string and $\Sigma$ is the character set, which in this case is the set of lowercase letters, so $|\Sigma| = 26$.
    
# Code    


#### Python3

```python
class Solution:
    def stringSequence(self, target: str) -> List[str]:
        ans = []
        for c in target:
            s = ans[-1] if ans else ""
            for a in ascii_lowercase:
                t = s + a
                ans.append(t)
                if a == c:
                    break
        return ans
```

#### Java

```java
class Solution {
    public List<String> stringSequence(String target) {
        List<String> ans = new ArrayList<>();
        for (char c : target.toCharArray()) {
            String s = ans.isEmpty() ? "" : ans.get(ans.size() - 1);
            for (char a = 'a'; a <= c; ++a) {
                String t = s + a;
                ans.add(t);
            }
        }
        return ans;
    }
}
```

#### C++

```cpp
class Solution {
public:
    vector<string> stringSequence(string target) {
        vector<string> ans;
        for (char c : target) {
            string s = ans.empty() ? "" : ans.back();
            for (char a = 'a'; a <= c; ++a) {
                string t = s + a;
                ans.push_back(t);
            }
        }
        return ans;
    }
};
```

#### Go

```go
func stringSequence(target string) (ans []string) {
	for _, c := range target {
		s := ""
		if len(ans) > 0 {
			s = ans[len(ans)-1]
		}
		for a := 'a'; a <= c; a++ {
			t := s + string(a)
			ans = append(ans, t)
		}
	}
	return
}
```

#### TypeScript

```ts
function stringSequence(target: string): string[] {
    const ans: string[] = [];
    for (const c of target) {
        let s = ans.length > 0 ? ans[ans.length - 1] : '';
        for (let a = 'a'.charCodeAt(0); a <= c.charCodeAt(0); a++) {
            const t = s + String.fromCharCode(a);
            ans.push(t);
        }
    }
    return ans;
}
```

