### Solution 1: Preprocessing + Greedy

According to the problem description,

If an integer $x$ is a prime number, then its largest proper divisor is $1$, so $x / 1 = x$, meaning $x$ cannot be divided further.

If an integer $x$ is not a prime number, we assume the largest proper divisor of $x$ is $y$, then $x / y$ must be a prime number. Therefore, we find the smallest prime factor $\textit{lpf}[x]$ such that $x \bmod \textit{lpf}[x] = 0$, making $x$ become $\textit{lpf}[x]$, at which point it cannot be divided further.

Thus, we can preprocess the smallest prime factor for each integer from $1$ to $10^6$. Then, we traverse the array from right to left. If the current element is greater than the next element, we change the current element to its smallest prime factor. If after changing the current element to its smallest prime factor it is still greater than the next element, it means the array cannot be made non-decreasing, and we return $-1$. Otherwise, we increment the operation count by one. Continue traversing until the entire array is processed.

The time complexity for preprocessing is $O(M \times \log \log M)$, where $M = 10^6$. The time complexity for traversing the array is $O(n)$, where $n$ is the length of the array. The space complexity is $O(M)$.
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
