# Solution: Hash Table

We traverse the $\textit{wordlist}$ and store the words in hash tables $\textit{low}$ and $\textit{pat}$ according to case-insensitive and vowel-insensitive rules, respectively. The key of $\textit{low}$ is the lowercase form of the word, and the key of $\textit{pat}$ is the string obtained by replacing the vowels of the word with `*`, with the value being the word itself. We use the hash table $\textit{s}$ to store the words in $\textit{wordlist}$.

We traverse $\textit{queries}$, for each word $\textit{q}$, if $\textit{q}$ is in $\textit{s}$, it means $\textit{q}$ is in $\textit{wordlist}$, and we directly add $\textit{q}$ to the answer array $\textit{ans}$; otherwise, if the lowercase form of $\textit{q}$ is in $\textit{low}$, it means $\textit{q}$ is in $\textit{wordlist}$ and is case-insensitive, and we add $\textit{low}[q.\text{lower}()]$ to the answer array $\textit{ans}$; otherwise, if the string obtained by replacing the vowels of $\textit{q}$ with `*` is in $\textit{pat}$, it means $\textit{q}$ is in $\textit{wordlist}$ and is vowel-insensitive, and we add $\textit{pat}[f(q)]$ to the answer array $\textit{ans}$; otherwise, it means $\textit{q}$ is not in $\textit{wordlist}$, and we add an empty string to the answer array $\textit{ans}$.

Finally, we return the answer array $\textit{ans}$.

The time complexity is $O(n + m)$, and the space complexity is $O(n)$, where $n$ and $m$ are the lengths of $\textit{wordlist}$ and $\textit{queries}$, respectively.

## Code


#### Python3

```python
class Solution:
    def spellchecker(self, wordlist: List[str], queries: List[str]) -> List[str]:
        def f(w):
            t = []
            for c in w:
                t.append("*" if c in "aeiou" else c)
            return "".join(t)

        s = set(wordlist)
        low, pat = {}, {}
        for w in wordlist:
            t = w.lower()
            low.setdefault(t, w)
            pat.setdefault(f(t), w)

        ans = []
        for q in queries:
            if q in s:
                ans.append(q)
                continue
            q = q.lower()
            if q in low:
                ans.append(low[q])
                continue
            q = f(q)
            if q in pat:
                ans.append(pat[q])
                continue
            ans.append("")
        return ans
```

#### Java

```java
class Solution {
    public String[] spellchecker(String[] wordlist, String[] queries) {
        Set<String> s = new HashSet<>();
        Map<String, String> low = new HashMap<>();
        Map<String, String> pat = new HashMap<>();
        for (String w : wordlist) {
            s.add(w);
            String t = w.toLowerCase();
            low.putIfAbsent(t, w);
            pat.putIfAbsent(f(t), w);
        }
        int m = queries.length;
        String[] ans = new String[m];
        for (int i = 0; i < m; ++i) {
            String q = queries[i];
            if (s.contains(q)) {
                ans[i] = q;
                continue;
            }
            q = q.toLowerCase();
            if (low.containsKey(q)) {
                ans[i] = low.get(q);
                continue;
            }
            q = f(q);
            if (pat.containsKey(q)) {
                ans[i] = pat.get(q);
                continue;
            }
            ans[i] = "";
        }
        return ans;
    }

    private String f(String w) {
        char[] cs = w.toCharArray();
        for (int i = 0; i < cs.length; ++i) {
            char c = cs[i];
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                cs[i] = '*';
            }
        }
        return String.valueOf(cs);
    }
}
```

#### C++

```cpp
class Solution {
public:
    vector<string> spellchecker(vector<string>& wordlist, vector<string>& queries) {
        unordered_set<string> s(wordlist.begin(), wordlist.end());
        unordered_map<string, string> low;
        unordered_map<string, string> pat;
        auto f = [](string& w) {
            string res;
            for (char& c : w) {
                if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                    res += '*';
                } else {
                    res += c;
                }
            }
            return res;
        };
        for (auto& w : wordlist) {
            string t = w;
            transform(t.begin(), t.end(), t.begin(), ::tolower);
            if (!low.count(t)) {
                low[t] = w;
            }
            t = f(t);
            if (!pat.count(t)) {
                pat[t] = w;
            }
        }
        vector<string> ans;
        for (auto& q : queries) {
            if (s.count(q)) {
                ans.emplace_back(q);
                continue;
            }
            transform(q.begin(), q.end(), q.begin(), ::tolower);
            if (low.count(q)) {
                ans.emplace_back(low[q]);
                continue;
            }
            q = f(q);
            if (pat.count(q)) {
                ans.emplace_back(pat[q]);
                continue;
            }
            ans.emplace_back("");
        }
        return ans;
    }
};
```

#### Go

```go
func spellchecker(wordlist []string, queries []string) (ans []string) {
	s := map[string]bool{}
	low := map[string]string{}
	pat := map[string]string{}
	f := func(w string) string {
		res := []byte(w)
		for i := range res {
			if res[i] == 'a' || res[i] == 'e' || res[i] == 'i' || res[i] == 'o' || res[i] == 'u' {
				res[i] = '*'
			}
		}
		return string(res)
	}
	for _, w := range wordlist {
		s[w] = true
		t := strings.ToLower(w)
		if _, ok := low[t]; !ok {
			low[t] = w
		}
		if _, ok := pat[f(t)]; !ok {
			pat[f(t)] = w
		}
	}
	for _, q := range queries {
		if s[q] {
			ans = append(ans, q)
			continue
		}
		q = strings.ToLower(q)
		if s, ok := low[q]; ok {
			ans = append(ans, s)
			continue
		}
		q = f(q)
		if s, ok := pat[q]; ok {
			ans = append(ans, s)
			continue
		}
		ans = append(ans, "")
	}
	return
}
```


## Contact information

If you have any questions or suggestions, please feel free to contact me at https://trinhminhtriet.com/
- Website: [trinhminhtriet.com](https://trinhminhtriet.com/)
- Github: [trinhminhtriet](https://github.com/trinhminhtriet)
- LinkedIn: [trinhminhtriet](https://www.linkedin.com/in/triet-trinh/)
- Twitter: [trinhminhtriet](https://twitter.com/trinhminhtriet)
- Email: [contact@trinhminhtriet.com](mailto:contact@trinhminhtriet.com)
