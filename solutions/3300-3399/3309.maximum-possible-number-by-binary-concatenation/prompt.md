### Solution 1: Enumeration

According to the problem description, the length of the array $\textit{nums}$ is $3$. We can enumerate all permutations of $\textit{nums}$, which has $3! = 6$ permutations. Then, we convert the elements of the permuted array into binary strings, concatenate these binary strings, and finally convert the concatenated binary string into a decimal number to get the maximum value.

The time complexity is $O(\log M)$, where $M$ represents the maximum value of the elements in $\textit{nums}$. The space complexity is $O(1)$.
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
