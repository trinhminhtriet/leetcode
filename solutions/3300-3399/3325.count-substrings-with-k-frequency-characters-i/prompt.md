### Solution 1: Sliding Window

We can enumerate the right endpoint of the substring, and then use a sliding window to maintain the left endpoint of the substring, ensuring that the occurrence count of each character in the sliding window is less than $k$.

We can use an array $\textit{cnt}$ to maintain the occurrence count of each character in the sliding window, then use a variable $\textit{l}$ to maintain the left endpoint of the sliding window, and use a variable $\textit{ans}$ to maintain the answer.

When we enumerate the right endpoint, we can add the character at the right endpoint to the sliding window, then check if the occurrence count of the character at the right endpoint in the sliding window is greater than or equal to $k$. If it is, we remove the character at the left endpoint from the sliding window until the occurrence count of each character in the sliding window is less than $k$. At this point, for substrings with left endpoints in the range $[0, ..l - 1]$ and right endpoint $r$, all satisfy the problem's requirements, so we add $l$ to the answer.

After enumeration, we return the answer.

The time complexity is $O(n)$, where $n$ is the length of the string $s$. The space complexity is $O(|\Sigma|)$, where $\Sigma$ is the character set, which in this case is the set of lowercase letters, so $|\Sigma| = 26$.
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
