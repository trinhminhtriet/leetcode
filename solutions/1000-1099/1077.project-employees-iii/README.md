---
comments: true
difficulty: Medium
tags:
    - Database
---

<!-- problem:start -->

# [1077. Project Employees III 🔒](https://leetcode.com/problems/project-employees-iii)

## Description

<!-- description:start -->

<p>Table: <code>Project</code></p>

<pre>
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| project_id  | int     |
| employee_id | int     |
+-------------+---------+
(project_id, employee_id) is the primary key (combination of columns with unique values) of this table.
employee_id is a foreign key (reference column) to <code>Employee</code> table.
Each row of this table indicates that the employee with employee_id is working on the project with project_id.
</pre>

<p>&nbsp;</p>

<p>Table: <code>Employee</code></p>

<pre>
+------------------+---------+
| Column Name      | Type    |
+------------------+---------+
| employee_id      | int     |
| name             | varchar |
| experience_years | int     |
+------------------+---------+
employee_id is the primary key (column with unique values) of this table.
Each row of this table contains information about one employee.
</pre>

<p>&nbsp;</p>

<p>Write a solution to report&nbsp;the <strong>most experienced</strong> employees in each project. In case of a tie, report all employees with the maximum number of experience years.</p>

<p>Return the result table in <strong>any order</strong>.</p>

<p>The&nbsp;result format is in the following example.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> 
Project table:
+-------------+-------------+
| project_id  | employee_id |
+-------------+-------------+
| 1           | 1           |
| 1           | 2           |
| 1           | 3           |
| 2           | 1           |
| 2           | 4           |
+-------------+-------------+
Employee table:
+-------------+--------+------------------+
| employee_id | name   | experience_years |
+-------------+--------+------------------+
| 1           | Khaled | 3                |
| 2           | Ali    | 2                |
| 3           | John   | 3                |
| 4           | Doe    | 2                |
+-------------+--------+------------------+
<strong>Output:</strong> 
+-------------+---------------+
| project_id  | employee_id   |
+-------------+---------------+
| 1           | 1             |
| 1           | 3             |
| 2           | 1             |
+-------------+---------------+
<strong>Explanation:</strong> Both employees with id 1 and 3 have the most experience among the employees of the first project. For the second project, the employee with id 1 has the most experience.
</pre>

<!-- description:end -->

## Solutions

<!-- solution:start -->

### Solution 1: Inner Join + Window Function

We can first perform an inner join between the `Project` table and the `Employee` table, and then use the window function `rank()` to group the `Project` table, sort it in descending order by `experience_years`, and finally select the most experienced employee for each project.

<!-- tabs:start -->

#### MySQL

```sql
# Write your MySQL query statement below
WITH
    T AS (
        SELECT
            *,
            RANK() OVER (
                PARTITION BY project_id
                ORDER BY experience_years DESC
            ) AS rk
        FROM
            Project
            JOIN Employee USING (employee_id)
    )
SELECT project_id, employee_id
FROM T
WHERE rk = 1;
```

<!-- tabs:end -->

<!-- solution:end -->

<!-- problem:end -->
