---
comments: true
difficulty: Medium
tags:
    - Database
---

<!-- problem:start -->

# [1875. Group Employees of the Same Salary 🔒](https://leetcode.com/problems/group-employees-of-the-same-salary)

## Description

<!-- description:start -->

<p>Table: <code>Employees</code></p>

<pre>
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| employee_id | int     |
| name        | varchar |
| salary      | int     |
+-------------+---------+
employee_id is the column with unique values for this table.
Each row of this table indicates the employee ID, employee name, and salary.
</pre>

<p>&nbsp;</p>

<p>A company wants to divide the employees into teams such that all the members on each team have the <strong>same salary</strong>. The teams should follow these criteria:</p>

<ul>
	<li>Each team should consist of <strong>at least two</strong> employees.</li>
	<li>All the employees on a team should have the <strong>same salary</strong>.</li>
	<li>All the employees of the same salary should be assigned to the same team.</li>
	<li>If the salary of an employee is unique, we <strong>do not</strong> assign this employee to any team.</li>
	<li>A team&#39;s ID is assigned based on the <strong>rank of the team&#39;s salary</strong> relative to the other teams&#39; salaries, where the team with the <strong>lowest</strong> salary has <code>team_id = 1</code>. Note that the salaries for employees not on a team are <strong>not included</strong> in this ranking.</li>
</ul>

<p>Write a solution to get the <code>team_id</code> of each employee that is in a team.</p>

<p>Return the result table ordered by <code>team_id</code> <strong>in ascending order</strong>. In case of a tie, order it by <code>employee_id</code> in <strong>ascending order</strong>.</p>

<p>The&nbsp;result format is in the following example.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> 
Employees table:
+-------------+---------+--------+
| employee_id | name    | salary |
+-------------+---------+--------+
| 2           | Meir    | 3000   |
| 3           | Michael | 3000   |
| 7           | Addilyn | 7400   |
| 8           | Juan    | 6100   |
| 9           | Kannon  | 7400   |
+-------------+---------+--------+
<strong>Output:</strong> 
+-------------+---------+--------+---------+
| employee_id | name    | salary | team_id |
+-------------+---------+--------+---------+
| 2           | Meir    | 3000   | 1       |
| 3           | Michael | 3000   | 1       |
| 7           | Addilyn | 7400   | 2       |
| 9           | Kannon  | 7400   | 2       |
+-------------+---------+--------+---------+
<strong>Explanation:</strong> 
Meir (employee_id=2) and Michael (employee_id=3) are in the same team because they have the same salary of 3000.
Addilyn (employee_id=7) and Kannon (employee_id=9) are in the same team because they have the same salary of 7400.
Juan (employee_id=8) is not included in any team because their salary of 6100 is unique (i.e. no other employee has the same salary).
The team IDs are assigned as follows (based on salary ranking, lowest first):
- team_id=1: Meir and Michael, a salary of 3000
- team_id=2: Addilyn and Kannon, a salary of 7400
Juan&#39;s salary of 6100 is not included in the ranking because they are not on a team.
</pre>

<!-- description:end -->

## Solutions

<!-- solution:start -->

### Solution 1

<!-- tabs:start -->

#### MySQL

```sql
# Write your MySQL query statement below
WITH
    S AS (
        SELECT salary
        FROM Employees
        GROUP BY salary
        HAVING COUNT(1) > 1
    ),
    T AS (
        SELECT salary, ROW_NUMBER() OVER (ORDER BY salary) AS team_id
        FROM S
    )
SELECT e.*, t.team_id
FROM
    Employees AS e
    JOIN T AS t ON e.salary = t.salary
ORDER BY 4, 1;
```

<!-- tabs:end -->

<!-- solution:end -->

<!-- problem:end -->
