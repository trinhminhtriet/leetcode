---
comments: true
difficulty: Easy
edit_url: https://github.com/doocs/leetcode/edit/main/solution/3400-3499/3436.Find%20Valid%20Emails/README_EN.md
tags:
    - Database
---

<!-- problem:start -->

# [3436. Find Valid Emails](https://leetcode.com/problems/find-valid-emails)

[中文文档](/solution/3400-3499/3436.Find%20Valid%20Emails/README.md)

## Description

<!-- description:start -->

<p>Table: <code>Users</code></p>

<pre>
+-----------------+---------+
| Column Name     | Type    |
+-----------------+---------+
| user_id         | int     |
| email           | varchar |
+-----------------+---------+
(user_id) is the unique key for this table.
Each row contains a user&#39;s unique ID and email address.
</pre>

<p>Write a solution to find all the <strong>valid email addresses</strong>. A valid email address meets the following criteria:</p>

<ul>
	<li>It contains exactly one <code>@</code> symbol.</li>
	<li>It ends with <code>.com</code>.</li>
	<li>The part before the <code>@</code> symbol contains only <strong>alphanumeric</strong> characters and <strong>underscores</strong>.</li>
	<li>The part after the <code>@</code> symbol and before <code>.com</code> contains a domain name <strong>that contains only letters</strong>.</li>
</ul>

<p>Return<em> the result table ordered by</em> <code>user_id</code> <em>in</em> <strong>ascending </strong><em>order</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example:</strong></p>

<div class="example-block">
<p><strong>Input:</strong></p>

<p>Users table:</p>

<pre class="example-io">
+---------+---------------------+
| user_id | email               |
+---------+---------------------+
| 1       | alice@example.com   |
| 2       | bob_at_example.com  |
| 3       | charlie@example.net |
| 4       | david@domain.com    |
| 5       | eve@invalid         |
+---------+---------------------+
</pre>

<p><strong>Output:</strong></p>

<pre class="example-io">
+---------+-------------------+
| user_id | email             |
+---------+-------------------+
| 1       | alice@example.com |
| 4       | david@domain.com  |
+---------+-------------------+
</pre>

<p><strong>Explanation:</strong></p>

<ul>
	<li><strong>alice@example.com</strong> is valid because it contains one <code>@</code>, alice&nbsp;is alphanumeric, and example.com&nbsp;starts with a letter and ends with .com.</li>
	<li><strong>bob_at_example.com</strong> is invalid because it contains an underscore instead of an <code>@</code>.</li>
	<li><strong>charlie@example.net</strong> is invalid because the domain does not end with <code>.com</code>.</li>
	<li><strong>david@domain.com</strong> is valid because it meets all criteria.</li>
	<li><strong>eve@invalid</strong> is invalid because the domain does not end with <code>.com</code>.</li>
</ul>

<p>Result table is ordered by user_id in ascending order.</p>
</div>

<!-- description:end -->

## Solutions

<!-- solution:start -->

### Solution 1: Regular Expression

We can use a regular expression with `REGEXP` to match valid email addresses.

The time complexity is $O(n)$, and the space complexity is $O(1)$. Here, $n$ is the length of the input string.

<!-- tabs:start -->

#### MySQL

```sql
# Write your MySQL query statement below
SELECT user_id, email
FROM Users
WHERE email REGEXP '^[A-Za-z0-9_]+@[A-Za-z][A-Za-z0-9]*\\.com$'
ORDER BY 1;
```

#### Pandas

```python
import pandas as pd


def find_valid_emails(users: pd.DataFrame) -> pd.DataFrame:
    email_pattern = r"^[A-Za-z0-9_]+@[A-Za-z][A-Za-z0-9]*\.com$"
    valid_emails = users[users["email"].str.match(email_pattern)]
    valid_emails = valid_emails.sort_values(by="user_id")
    return valid_emails
```

<!-- tabs:end -->

<!-- solution:end -->

<!-- problem:end -->
