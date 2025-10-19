# Intuition

We are asked to find the **lexicographically smallest string** that can be formed by repeatedly applying two operations on a given numeric string `s`:

1. **Add operation:** Add `a` to each digit at odd indices (wrapping around with modulo 10).
2. **Rotate operation:** Rotate the string to the right by `b` positions.

Because both operations can be applied any number of times, there may be many possible strings. However, since the number of possible unique strings is finite, we can explore all reachable strings using a **Breadth-First Search (BFS)** until no new strings appear.

---

# Approach

1. **Use BFS traversal:**

   * Start from the initial string `s`.
   * Use a queue (`q`) to store strings to be processed and a set (`vis`) to record visited strings to avoid repetition.
2. **At each step:**

   * Compare the current string with the current best `ans` and update `ans` if it is smaller.
   * Generate two new strings:

     * **Add operation (`t1`)**: For every odd index `i`, increase `s[i]` by `a` modulo 10.
     * **Rotate operation (`t2`)**: Rotate `s` right by `b` positions.
   * If either `t1` or `t2` hasn’t been seen before, add it to the queue and mark it as visited.
3. **Repeat** until the queue is empty — meaning all reachable strings have been explored.
4. **Return** the smallest string found.

This guarantees that all possible transformations are checked efficiently.

---

# Complexity

* **Time complexity:**
  (O(10 \times n)) in practice, since the number of possible unique strings is limited by digit variations and rotations. BFS explores each unique string once, and each transformation is (O(n)).

* **Space complexity:**
  (O(10^n)) in the worst case theoretically (all unique strings stored), but practically (O(n)) for typical constraints due to digit and rotation limits.

---

# Code

```java []
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

```cpp []
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

```golang []
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

# Contact information

If you have any questions or suggestions, please feel free to contact me at https://trinhminhtriet.com/
- Website: [trinhminhtriet.com](https://trinhminhtriet.com/)
- Github: [trinhminhtriet](https://github.com/trinhminhtriet)
- LinkedIn: [trinhminhtriet](https://www.linkedin.com/in/triet-trinh/)
- Twitter: [trinhminhtriet](https://twitter.com/trinhminhtriet)
- Email: [contact@trinhminhtriet.com](mailto:contact@trinhminhtriet.com)
