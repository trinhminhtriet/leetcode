---
comments: true
difficulty: Medium
tags:
    - Database
---

<!-- problem:start -->

# [1709. Biggest Window Between Visits 🔒](https://leetcode.com/problems/biggest-window-between-visits)

## Description

<!-- description:start -->

<p>Table: <code>UserVisits</code></p>

<pre>
+-------------+------+
| Column Name | Type |
+-------------+------+
| user_id     | int  |
| visit_date  | date |
+-------------+------+
This table does not have a primary key, it might contain duplicate rows.
This table contains logs of the dates that users visited a certain retailer.
</pre>

<p>&nbsp;</p>

<p>Assume today&#39;s date is <code>&#39;2021-1-1&#39;</code>.</p>

<p>Write a solution that will, for each <code>user_id</code>, find out the largest <code>window</code> of days between each visit and the one right after it (or today if you are considering the last visit).</p>

<p>Return the result table ordered by <code>user_id</code>.</p>

<p>The query result format is in the following example.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> 
UserVisits table:
+---------+------------+
| user_id | visit_date |
+---------+------------+
| 1       | 2020-11-28 |
| 1       | 2020-10-20 |
| 1       | 2020-12-3  |
| 2       | 2020-10-5  |
| 2       | 2020-12-9  |
| 3       | 2020-11-11 |
+---------+------------+
<strong>Output:</strong> 
+---------+---------------+
| user_id | biggest_window|
+---------+---------------+
| 1       | 39            |
| 2       | 65            |
| 3       | 51            |
+---------+---------------+
<strong>Explanation:</strong> 
For the first user, the windows in question are between dates:
    - 2020-10-20 and 2020-11-28 with a total of 39 days. 
    - 2020-11-28 and 2020-12-3 with a total of 5 days. 
    - 2020-12-3 and 2021-1-1 with a total of 29 days.
Making the biggest window the one with 39 days.
For the second user, the windows in question are between dates:
    - 2020-10-5 and 2020-12-9 with a total of 65 days.
    - 2020-12-9 and 2021-1-1 with a total of 23 days.
Making the biggest window the one with 65 days.
For the third user, the only window in question is between dates 2020-11-11 and 2021-1-1 with a total of 51 days.
</pre>

<!-- description:end -->

## Solutions

<!-- solution:start -->

### Solution 1: Window Function

We can use the window function `LEAD` to obtain the date of the next visit for each user (if the date of the next visit does not exist, it is considered as `2021-1-1`), and then use the `DATEDIFF` function to calculate the number of days between two visits. Finally, we can take the maximum value of the number of days between visits for each user.

<!-- tabs:start -->

#### MySQL

```sql
# Write your MySQL query statement below
WITH
    T AS (
        SELECT
            user_id,
            DATEDIFF(
                LEAD(visit_date, 1, '2021-1-1') OVER (
                    PARTITION BY user_id
                    ORDER BY visit_date
                ),
                visit_date
            ) AS diff
        FROM UserVisits
    )
SELECT user_id, MAX(diff) AS biggest_window
FROM T
GROUP BY 1
ORDER BY 1;
```

<!-- tabs:end -->

<!-- solution:end -->

<!-- problem:end -->
