### Solution 1: Memoization Search

We design a function $\textit{dfs}(i, j, k)$, where $i$ represents starting from the $i$-th character of the string $s$, $j$ represents the current score difference between $\textit{Alice}$ and $\textit{Bob}$, and $k$ represents the last creature summoned by $\textit{Bob}$. The function calculates how many sequences of moves $\textit{Bob}$ can make to defeat $\textit{Alice}$.

The answer is $\textit{dfs}(0, 0, -1)$, where $-1$ indicates that $\textit{Bob}$ has not summoned any creatures yet. In languages other than Python, since the score difference can be negative, we can add $n$ to the score difference to ensure it is non-negative.

The calculation process of the function $\textit{dfs}(i, j, k)$ is as follows:

-   If $n - i \leq j$, then the remaining rounds are not enough for $\textit{Bob}$ to surpass $\textit{Alice}$'s score, so return $0$.
-   If $i \geq n$, then all rounds have ended. If $\textit{Bob}$'s score is less than $0$, return $1$; otherwise, return $0$.
-   Otherwise, we enumerate the creatures $\textit{Bob}$ can summon this round. If the creature summoned this round is the same as the one summoned in the previous round, $\textit{Bob}$ cannot win this round, so we skip it. Otherwise, we recursively calculate $\textit{dfs}(i + 1, j + \textit{calc}(d[s[i]], l), l)$, where $\textit{calc}(x, y)$ represents the outcome between $x$ and $y$, and $d$ is a mapping that maps characters to $\textit{012}$. We sum all the results and take the modulo $10^9 + 7$.

The time complexity is $O(n^2 \times k^2)$, where $n$ is the length of the string $s$, and $k$ represents the size of the character set. The space complexity is $O(n^2 \times k)$.
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
