### Solution 1: Hash Table + Ordered Set

We use a hash table $\textit{cnt}$ to count the occurrences of each element in the window, an ordered set $\textit{l}$ to store the $x$ elements with the highest occurrences in the window, and another ordered set $\textit{r}$ to store the remaining elements.

We maintain a variable $\textit{s}$ to represent the sum of the elements in $\textit{l}$. Initially, we add the first $k$ elements to the window, update the ordered sets $\textit{l}$ and $\textit{r}$, and calculate the value of $\textit{s}$. If the size of $\textit{l}$ is less than $x$ and $\textit{r}$ is not empty, we repeatedly move the largest element from $\textit{r}$ to $\textit{l}$ until the size of $\textit{l}$ equals $x$, updating the value of $\textit{s}$ in the process. If the size of $\textit{l}$ is greater than $x$, we repeatedly move the smallest element from $\textit{l}$ to $\textit{r}$ until the size of $\textit{l}$ equals $x$, updating the value of $\textit{s}$ in the process. At this point, we can calculate the current window's $\textit{x-sum}$ and add it to the answer array. Then we remove the left boundary element of the window, update $\textit{cnt}$, and update the ordered sets $\textit{l}$ and $\textit{r}$, as well as the value of $\textit{s}$. Continue traversing the array until the traversal is complete.

The time complexity is $O(n \times \log k)$, and the space complexity is $O(n)$. Here, $n$ is the length of the array $\textit{nums}$.

Similar problems:

-   [3013. Divide an Array Into Subarrays With Minimum Cost II](/solution/3000-3099/3013.Divide%20an%20Array%20Into%20Subarrays%20With%20Minimum%20Cost%20II/README_EN.md)
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
