---
comments: true
difficulty: Easy
tags:
    - Greedy
    - Array
    - Math
    - Sorting
---

<!-- problem:start -->

# [976. Largest Perimeter Triangle](https://leetcode.com/problems/largest-perimeter-triangle)

## Description

<!-- description:start -->

<p>Given an integer array <code>nums</code>, return <em>the largest perimeter of a triangle with a non-zero area, formed from three of these lengths</em>. If it is impossible to form any triangle of a non-zero area, return <code>0</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [2,1,2]
<strong>Output:</strong> 5
<strong>Explanation:</strong> You can form a triangle with three side lengths: 1, 2, and 2.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,2,1,10]
<strong>Output:</strong> 0
<strong>Explanation:</strong> 
You cannot use the side lengths 1, 1, and 2 to form a triangle.
You cannot use the side lengths 1, 1, and 10 to form a triangle.
You cannot use the side lengths 1, 2, and 10 to form a triangle.
As we cannot use any three side lengths to form a triangle of non-zero area, we return 0.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>3 &lt;= nums.length &lt;= 10<sup>4</sup></code></li>
	<li><code>1 &lt;= nums[i] &lt;= 10<sup>6</sup></code></li>
</ul>

<!-- description:end -->

## Solutions

<!-- solution:start -->

### Solution 1

<!-- tabs:start -->

#### Python3

```python
class Solution:
    def largestPerimeter(self, nums: List[int]) -> int:
        nums.sort()
        for i in range(len(nums) - 1, 1, -1):
            if (c := nums[i - 1] + nums[i - 2]) > nums[i]:
                return c + nums[i]
        return 0
```

#### Java

```java
class Solution {
    public int largestPerimeter(int[] nums) {
        Arrays.sort(nums);
        for (int i = nums.length - 1; i >= 2; --i) {
            int c = nums[i - 1] + nums[i - 2];
            if (c > nums[i]) {
                return c + nums[i];
            }
        }
        return 0;
    }
}
```

#### C++

```cpp
class Solution {
public:
    int largestPerimeter(vector<int>& nums) {
        sort(nums.begin(), nums.end());
        for (int i = nums.size() - 1; i >= 2; --i) {
            int c = nums[i - 1] + nums[i - 2];
            if (c > nums[i]) return c + nums[i];
        }
        return 0;
    }
};
```

#### Go

```go
func largestPerimeter(nums []int) int {
	sort.Ints(nums)
	for i := len(nums) - 1; i >= 2; i-- {
		c := nums[i-1] + nums[i-2]
		if c > nums[i] {
			return c + nums[i]
		}
	}
	return 0
}
```

#### TypeScript

```ts
function largestPerimeter(nums: number[]): number {
    const n = nums.length;
    nums.sort((a, b) => b - a);
    for (let i = 2; i < n; i++) {
        const [a, b, c] = [nums[i - 2], nums[i - 1], nums[i]];
        if (a < b + c) {
            return a + b + c;
        }
    }
    return 0;
}
```

#### Rust

```rust
impl Solution {
    pub fn largest_perimeter(mut nums: Vec<i32>) -> i32 {
        let n = nums.len();
        nums.sort_unstable_by(|a, b| b.cmp(&a));
        for i in 2..n {
            let (a, b, c) = (nums[i - 2], nums[i - 1], nums[i]);
            if a < b + c {
                return a + b + c;
            }
        }
        0
    }
}
```

#### C

```c
int cmp(const void* a, const void* b) {
    return *(int*) b - *(int*) a;
}

int largestPerimeter(int* nums, int numsSize) {
    qsort(nums, numsSize, sizeof(int), cmp);
    for (int i = 2; i < numsSize; i++) {
        if (nums[i - 2] < nums[i - 1] + nums[i]) {
            return nums[i - 2] + nums[i - 1] + nums[i];
        }
    }
    return 0;
}
```

<!-- tabs:end -->

<!-- solution:end -->

<!-- problem:end -->
