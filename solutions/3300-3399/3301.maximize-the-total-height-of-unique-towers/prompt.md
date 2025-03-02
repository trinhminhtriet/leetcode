### Solution 1: Sorting + Greedy

We can sort the maximum heights of the towers in descending order, then allocate the heights one by one starting from the maximum height. Use a variable $mx$ to record the current maximum allocated height.

If the current height $x$ is greater than $mx - 1$, update $x$ to $mx - 1$. If $x$ is less than or equal to $0$, it means the height cannot be allocated, and we directly return $-1$. Otherwise, we add $x$ to the answer and update $mx$ to $x$.

Finally, return the answer.

The time complexity is $O(n \times \log n)$, and the space complexity is $O(\log n)$. Here, $n$ is the length of the array $\textit{maximumHeight}$.
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
