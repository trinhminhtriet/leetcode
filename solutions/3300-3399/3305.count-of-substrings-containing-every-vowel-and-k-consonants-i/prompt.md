### Solution 1: Problem Transformation + Sliding Window

We can transform the problem into solving the following two subproblems:

1. Find the total number of substrings where each vowel appears at least once and contains at least $k$ consonants, denoted as $\textit{f}(k)$;
2. Find the total number of substrings where each vowel appears at least once and contains at least $k + 1$ consonants, denoted as $\textit{f}(k + 1)$.

Then the answer is $\textit{f}(k) - \textit{f}(k + 1)$.

Therefore, we design a function $\textit{f}(k)$ to count the total number of substrings where each vowel appears at least once and contains at least $k$ consonants.

We can use a hash table $\textit{cnt}$ to count the occurrences of each vowel, a variable $\textit{ans}$ to store the answer, a variable $\textit{l}$ to record the left boundary of the sliding window, and a variable $\textit{x}$ to record the number of consonants in the current window.

Traverse the string. If the current character is a vowel, add it to the hash table $\textit{cnt}$; otherwise, increment $\textit{x}$ by one. If $\textit{x} \ge k$ and the size of the hash table $\textit{cnt}$ is $5$, it means the current window meets the conditions. We then move the left boundary in a loop until the window no longer meets the conditions. At this point, all substrings ending at the right boundary $\textit{r}$ and with the left boundary in the range $[0, .. \textit{l} - 1]$ meet the conditions, totaling $\textit{l}$ substrings. We add $\textit{l}$ to the answer. Continue traversing the string until the end, and we get $\textit{f}(k)$.

Finally, we return $\textit{f}(k) - \textit{f}(k + 1)$.

The time complexity is $O(n)$, where $n$ is the length of the string $\textit{word}$. The space complexity is $O(1)$.
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
