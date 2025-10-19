### Solution
---
Rewrite solution above by this Markdown format:

# Intuition

# Approach

# Complexity
- Time complexity:

- Space complexity:

# Code

```java
class Solution {
    public String findLexSmallestString(String s, int a, int b) {
        Deque<String> q = new ArrayDeque<>();
        q.offer(s);
        Set<String> vis = new HashSet<>();
        vis.add(s);
        String ans = s;
        int n = s.length();
        while (!q.isEmpty()) {
            s = q.poll();
            if (ans.compareTo(s) > 0) {
                ans = s;
            }
            char[] cs = s.toCharArray();
            for (int i = 1; i < n; i += 2) {
                cs[i] = (char) (((cs[i] - '0' + a) % 10) + '0');
            }
            String t1 = String.valueOf(cs);
            String t2 = s.substring(n - b) + s.substring(0, n - b);
            for (String t : List.of(t1, t2)) {
                if (vis.add(t)) {
                    q.offer(t);
                }
            }
        }
        return ans;
    }
}
```

```cpp
class Solution {
public:
    string findLexSmallestString(string s, int a, int b) {
        queue<string> q{{s}};
        unordered_set<string> vis{{s}};
        string ans = s;
        int n = s.size();
        while (!q.empty()) {
            s = q.front();
            q.pop();
            ans = min(ans, s);
            string t1 = s;
            for (int i = 1; i < n; i += 2) {
                t1[i] = (t1[i] - '0' + a) % 10 + '0';
            }
            string t2 = s.substr(n - b) + s.substr(0, n - b);
            for (auto& t : {t1, t2}) {
                if (!vis.count(t)) {
                    vis.insert(t);
                    q.emplace(t);
                }
            }
        }
        return ans;
    }
};
```

```go
func findLexSmallestString(s string, a int, b int) string {
	q := []string{s}
	vis := map[string]bool{s: true}
	ans := s
	n := len(s)
	for len(q) > 0 {
		s = q[0]
		q = q[1:]
		if ans > s {
			ans = s
		}
		t1 := []byte(s)
		for i := 1; i < n; i += 2 {
			t1[i] = byte((int(t1[i]-'0')+a)%10 + '0')
		}
		t2 := s[n-b:] + s[:n-b]
		for _, t := range []string{string(t1), t2} {
			if !vis[t] {
				vis[t] = true
				q = append(q, t)
			}
		}
	}
	return ans
}
```

