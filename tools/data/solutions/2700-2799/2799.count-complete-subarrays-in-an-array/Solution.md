### Solution 1

## Code


#### Python3

```python
class Solution:
    def countCompleteSubarrays(self, nums: List[int]) -> int:
        cnt = len(set(nums))
        ans, n = 0, len(nums)
        for i in range(n):
            s = set()
            for x in nums[i:]:
                s.add(x)
                if len(s) == cnt:
                    ans += 1
        return ans
```

#### Java

```java
class Solution {
    public int countCompleteSubarrays(int[] nums) {
        Set<Integer> s = new HashSet<>();
        for (int x : nums) {
            s.add(x);
        }
        int cnt = s.size();
        int ans = 0, n = nums.length;
        for (int i = 0; i < n; ++i) {
            s.clear();
            for (int j = i; j < n; ++j) {
                s.add(nums[j]);
                if (s.size() == cnt) {
                    ++ans;
                }
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
    int countCompleteSubarrays(vector<int>& nums) {
        unordered_set<int> s(nums.begin(), nums.end());
        int cnt = s.size();
        int ans = 0, n = nums.size();
        for (int i = 0; i < n; ++i) {
            s.clear();
            for (int j = i; j < n; ++j) {
                s.insert(nums[j]);
                if (s.size() == cnt) {
                    ++ans;
                }
            }
        }
        return ans;
    }
};
```

#### Go

```go
func countCompleteSubarrays(nums []int) (ans int) {
	s := map[int]bool{}
	for _, x := range nums {
		s[x] = true
	}
	cnt := len(s)
	for i := range nums {
		s = map[int]bool{}
		for _, x := range nums[i:] {
			s[x] = true
			if len(s) == cnt {
				ans++
			}
		}
	}
	return
}
```

#### TypeScript

```ts
function countCompleteSubarrays(nums: number[]): number {
    const s: Set<number> = new Set(nums);
    const cnt = s.size;
    const n = nums.length;
    let ans = 0;
    for (let i = 0; i < n; ++i) {
        s.clear();
        for (let j = i; j < n; ++j) {
            s.add(nums[j]);
            if (s.size === cnt) {
                ++ans;
            }
        }
    }
    return ans;
}
```

#### Rust

```rust
use std::collections::HashSet;
impl Solution {
    pub fn count_complete_subarrays(nums: Vec<i32>) -> i32 {
        let mut set: HashSet<&i32> = nums.iter().collect();
        let n = nums.len();
        let m = set.len();
        let mut ans = 0;
        for i in 0..n {
            set.clear();
            for j in i..n {
                set.insert(&nums[j]);
                if set.len() == m {
                    ans += n - j;
                    break;
                }
            }
        }
        ans as i32
    }
}
```


## Contact information

If you have any questions or suggestions, please feel free to contact me at https://trinhminhtriet.com/
- Website: [trinhminhtriet.com](https://trinhminhtriet.com/)
- Github: [trinhminhtriet](https://github.com/trinhminhtriet)
- LinkedIn: [trinhminhtriet](https://www.linkedin.com/in/triet-trinh/)
- Twitter: [trinhminhtriet](https://twitter.com/trinhminhtriet)
- Email: [contact@trinhminhtriet.com](mailto:contact@trinhminhtriet.com)
