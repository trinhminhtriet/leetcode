---
comments: true
difficulty: Medium
tags:
    - Shell
---

<!-- problem:start -->

# [192. Word Frequency](https://leetcode.com/problems/word-frequency)

## Description

<!-- description:start -->

<p>Write a bash script to calculate the <span data-keyword="frequency-textfile">frequency</span> of each word in a text file <code>words.txt</code>.</p>

<p>For simplicity sake, you may assume:</p>

<ul>
	<li><code>words.txt</code> contains only lowercase characters and space <code>&#39; &#39;</code> characters.</li>
	<li>Each word must consist of lowercase characters only.</li>
	<li>Words are separated by one or more whitespace characters.</li>
</ul>

<p><strong class="example">Example:</strong></p>

<p>Assume that <code>words.txt</code> has the following content:</p>

<pre>
the day is sunny the the
the sunny is is
</pre>

<p>Your script should output the following, sorted by descending frequency:</p>

<pre>
the 4
is 3
sunny 2
day 1
</pre>

<p><b>Note:</b></p>

<ul>
	<li>Don&#39;t worry about handling ties, it is guaranteed that each word&#39;s frequency count is unique.</li>
	<li>Could you write it in one-line using <a href="http://tldp.org/HOWTO/Bash-Prog-Intro-HOWTO-4.html">Unix pipes</a>?</li>
</ul>

<!-- description:end -->

## Solutions

<!-- solution:start -->

### Solution 1: awk

<!-- tabs:start -->

#### Shell

```bash
# Read from the file words.txt and output the word frequency list to stdout.
cat words.txt | tr -s ' ' '\n' | sort | uniq -c | sort -nr | awk '{print $2, $1}'
```

<!-- tabs:end -->

<!-- solution:end -->

<!-- problem:end -->
