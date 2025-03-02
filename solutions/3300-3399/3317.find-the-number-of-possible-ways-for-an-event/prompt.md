### Solution 1: Dynamic Programming

We define $f[i][j]$ to represent the number of ways to arrange the first $i$ performers into $j$ programs. Initially, $f[0][0] = 1$, and the rest $f[i][j] = 0$.

For $f[i][j]$, where $1 \leq i \leq n$ and $1 \leq j \leq x$, we consider the $i$-th performer:

-   If the performer is assigned to a program that already has performers, there are $j$ choices, i.e., $f[i - 1][j] \times j$;
-   If the performer is assigned to a program that has no performers, there are $x - (j - 1)$ choices, i.e., $f[i - 1][j - 1] \times (x - (j - 1))$.

So the state transition equation is:

$$
f[i][j] = f[i - 1][j] \times j + f[i - 1][j - 1] \times (x - (j - 1))
$$

For each $j$, there are $y^j$ choices, so the final answer is:

$$
\sum_{j = 1}^{x} f[n][j] \times y^j
$$

Note that since the answer can be very large, we need to take the modulo $10^9 + 7$.

The time complexity is $O(n \times x)$, and the space complexity is $O(n \times x)$. Here, $n$ and $x$ represent the number of performers and the number of programs, respectively.
---
Rewrite solution above by this Markdown format:

# Intuition
<!-- Describe your first thoughts on how to solve this problem. -->

# Approach
<!-- Describe your approach to solving the problem. -->

# Complexity
- Time complexity:
<!-- Add your time complexity here, e.g. $$O(n)$$ -->

- Space complexity:
<!-- Add your space complexity here, e.g. $$O(n)$$ -->
