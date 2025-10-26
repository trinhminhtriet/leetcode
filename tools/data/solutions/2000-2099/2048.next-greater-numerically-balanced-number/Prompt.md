### Solution 1: Enumeration

We note that the range of $n$ in the problem is $[0, 10^6]$, and one of the balanced numbers greater than $10^6$ is $1224444$. Therefore, we directly enumerate $x \in [n + 1, ..]$ and then judge whether $x$ is a balanced number. The enumerated $x$ will definitely not exceed $1224444$.

The time complexity is $O(M - n)$, where $M = 1224444$. The space complexity is $O(1)$.
---
Rewrite solution above by this Markdown format:

# Intuition

# Approach

# Complexity
- Time complexity:

- Space complexity:

# Code

```java []
```

````python []
```

