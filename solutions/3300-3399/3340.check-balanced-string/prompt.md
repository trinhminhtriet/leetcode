### Solution 1: Simulation

We can use an array $f$ of length $2$ to record the sum of numbers at even indices and odd indices. Then, we traverse the string $\textit{nums}$ and add the numbers to the corresponding positions based on the parity of the indices. Finally, we check whether $f[0]$ is equal to $f[1]$.

The time complexity is $O(n)$, where $n$ is the length of the string $\textit{nums}$. The space complexity is $O(1)$.
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
