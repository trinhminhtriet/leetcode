---
comments: true
difficulty: Medium
rating: 1616
source: Biweekly Contest 76 Q3
tags:
    - Greedy
    - Design
    - Array
---

<!-- problem:start -->

# [2241. Design an ATM Machine](https://leetcode.com/problems/design-an-atm-machine)

## Description

<!-- description:start -->

<p>There is an ATM machine that stores banknotes of <code>5</code> denominations: <code>20</code>, <code>50</code>, <code>100</code>, <code>200</code>, and <code>500</code> dollars. Initially the ATM is empty. The user can use the machine to deposit or withdraw any amount of money.</p>

<p>When withdrawing, the machine prioritizes using banknotes of <strong>larger</strong> values.</p>

<ul>
	<li>For example, if you want to withdraw <code>$300</code> and there are <code>2</code> <code>$50</code> banknotes, <code>1</code> <code>$100</code> banknote, and <code>1</code> <code>$200</code> banknote, then the machine will use the <code>$100</code> and <code>$200</code> banknotes.</li>
	<li>However, if you try to withdraw <code>$600</code> and there are <code>3</code> <code>$200</code> banknotes and <code>1</code> <code>$500</code> banknote, then the withdraw request will be rejected because the machine will first try to use the <code>$500</code> banknote and then be unable to use banknotes to complete the remaining <code>$100</code>. Note that the machine is <strong>not</strong> allowed to use the <code>$200</code> banknotes instead of the <code>$500</code> banknote.</li>
</ul>

<p>Implement the ATM class:</p>

<ul>
	<li><code>ATM()</code> Initializes the ATM object.</li>
	<li><code>void deposit(int[] banknotesCount)</code> Deposits new banknotes in the order <code>$20</code>, <code>$50</code>, <code>$100</code>, <code>$200</code>, and <code>$500</code>.</li>
	<li><code>int[] withdraw(int amount)</code> Returns an array of length <code>5</code> of the number of banknotes that will be handed to the user in the order <code>$20</code>, <code>$50</code>, <code>$100</code>, <code>$200</code>, and <code>$500</code>, and update the number of banknotes in the ATM after withdrawing. Returns <code>[-1]</code> if it is not possible (do <strong>not</strong> withdraw any banknotes in this case).</li>
</ul>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input</strong>
[&quot;ATM&quot;, &quot;deposit&quot;, &quot;withdraw&quot;, &quot;deposit&quot;, &quot;withdraw&quot;, &quot;withdraw&quot;]
[[], [[0,0,1,2,1]], [600], [[0,1,0,1,1]], [600], [550]]
<strong>Output</strong>
[null, null, [0,0,1,0,1], null, [-1], [0,1,0,0,1]]

<strong>Explanation</strong>
ATM atm = new ATM();
atm.deposit([0,0,1,2,1]); // Deposits 1 $100 banknote, 2 $200 banknotes,
                          // and 1 $500 banknote.
atm.withdraw(600);        // Returns [0,0,1,0,1]. The machine uses 1 $100 banknote
                          // and 1 $500 banknote. The banknotes left over in the
                          // machine are [0,0,0,2,0].
atm.deposit([0,1,0,1,1]); // Deposits 1 $50, $200, and $500 banknote.
                          // The banknotes in the machine are now [0,1,0,3,1].
atm.withdraw(600);        // Returns [-1]. The machine will try to use a $500 banknote
                          // and then be unable to complete the remaining $100,
                          // so the withdraw request will be rejected.
                          // Since the request is rejected, the number of banknotes
                          // in the machine is not modified.
atm.withdraw(550);        // Returns [0,1,0,0,1]. The machine uses 1 $50 banknote
                          // and 1 $500 banknote.</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>banknotesCount.length == 5</code></li>
	<li><code>0 &lt;= banknotesCount[i] &lt;= 10<sup>9</sup></code></li>
	<li><code>1 &lt;= amount &lt;= 10<sup>9</sup></code></li>
	<li>At most <code>5000</code> calls <strong>in total</strong> will be made to <code>withdraw</code> and <code>deposit</code>.</li>
	<li>At least <strong>one</strong> call will be made to each function <code>withdraw</code> and <code>deposit</code>.</li>
	<li>Sum of <code>banknotesCount[i]</code> in all deposits doesn&#39;t exceed <code>10<sup>9</sup></code></li>
</ul>

<!-- description:end -->

## Solutions

<!-- solution:start -->

### Solution 1: Simulation

We use an array $\textit{d}$ to record the denominations of the bills and an array $\textit{cnt}$ to record the number of bills for each denomination.

For the `deposit` operation, we simply add the number of bills to the corresponding denomination. The time complexity is $O(1)$.

For the `withdraw` operation, we enumerate the bills from largest to smallest denomination, taking out as many bills as possible without exceeding $\textit{amount}$. We then subtract the total value of the withdrawn bills from $\textit{amount}$. If $\textit{amount}$ is still greater than $0$ at the end, it means we cannot withdraw the requested amount, and we return $-1$. Otherwise, we return the number of withdrawn bills. The time complexity is $O(1)$.

<!-- tabs:start -->

#### Python3

```python
class ATM:
    def __init__(self):
        self.d = [20, 50, 100, 200, 500]
        self.m = len(self.d)
        self.cnt = [0] * self.m

    def deposit(self, banknotesCount: List[int]) -> None:
        for i, x in enumerate(banknotesCount):
            self.cnt[i] += x

    def withdraw(self, amount: int) -> List[int]:
        ans = [0] * self.m
        for i in reversed(range(self.m)):
            ans[i] = min(amount // self.d[i], self.cnt[i])
            amount -= ans[i] * self.d[i]
        if amount > 0:
            return [-1]
        for i, x in enumerate(ans):
            self.cnt[i] -= x
        return ans


# Your ATM object will be instantiated and called as such:
# obj = ATM()
# obj.deposit(banknotesCount)
# param_2 = obj.withdraw(amount)
```

#### Java

```java
class ATM {
    private int[] d = {20, 50, 100, 200, 500};
    private int m = d.length;
    private long[] cnt = new long[5];

    public ATM() {
    }

    public void deposit(int[] banknotesCount) {
        for (int i = 0; i < banknotesCount.length; ++i) {
            cnt[i] += banknotesCount[i];
        }
    }

    public int[] withdraw(int amount) {
        int[] ans = new int[m];
        for (int i = m - 1; i >= 0; --i) {
            ans[i] = (int) Math.min(amount / d[i], cnt[i]);
            amount -= ans[i] * d[i];
        }
        if (amount > 0) {
            return new int[] {-1};
        }
        for (int i = 0; i < m; ++i) {
            cnt[i] -= ans[i];
        }
        return ans;
    }
}

/**
 * Your ATM object will be instantiated and called as such:
 * ATM obj = new ATM();
 * obj.deposit(banknotesCount);
 * int[] param_2 = obj.withdraw(amount);
 */
```

#### C++

```cpp
class ATM {
public:
    ATM() {
    }

    void deposit(vector<int> banknotesCount) {
        for (int i = 0; i < banknotesCount.size(); ++i) {
            cnt[i] += banknotesCount[i];
        }
    }

    vector<int> withdraw(int amount) {
        vector<int> ans(m);
        for (int i = m - 1; ~i; --i) {
            ans[i] = min(1ll * amount / d[i], cnt[i]);
            amount -= ans[i] * d[i];
        }
        if (amount > 0) {
            return {-1};
        }
        for (int i = 0; i < m; ++i) {
            cnt[i] -= ans[i];
        }
        return ans;
    }

private:
    static constexpr int d[5] = {20, 50, 100, 200, 500};
    static constexpr int m = size(d);
    long long cnt[m] = {0};
};

/**
 * Your ATM object will be instantiated and called as such:
 * ATM* obj = new ATM();
 * obj->deposit(banknotesCount);
 * vector<int> param_2 = obj->withdraw(amount);
 */
```

#### Go

```go
var d = [...]int{20, 50, 100, 200, 500}

const m = len(d)

type ATM [m]int

func Constructor() ATM {
	return ATM{}
}

func (this *ATM) Deposit(banknotesCount []int) {
	for i, x := range banknotesCount {
		this[i] += x
	}
}

func (this *ATM) Withdraw(amount int) []int {
	ans := make([]int, m)
	for i := m - 1; i >= 0; i-- {
		ans[i] = min(amount/d[i], this[i])
		amount -= ans[i] * d[i]
	}
	if amount > 0 {
		return []int{-1}
	}
	for i, x := range ans {
		this[i] -= x
	}
	return ans
}

/**
 * Your ATM object will be instantiated and called as such:
 * obj := Constructor();
 * obj.Deposit(banknotesCount);
 * param_2 := obj.Withdraw(amount);
 */
```

#### TypeScript

```ts
const d: number[] = [20, 50, 100, 200, 500];
const m = d.length;

class ATM {
    private cnt: number[];

    constructor() {
        this.cnt = Array(m).fill(0);
    }

    deposit(banknotesCount: number[]): void {
        for (let i = 0; i < banknotesCount.length; ++i) {
            this.cnt[i] += banknotesCount[i];
        }
    }

    withdraw(amount: number): number[] {
        const ans: number[] = Array(m).fill(0);
        for (let i = m - 1; i >= 0; --i) {
            ans[i] = Math.min(Math.floor(amount / d[i]), this.cnt[i]);
            amount -= ans[i] * d[i];
        }
        if (amount > 0) {
            return [-1];
        }
        for (let i = 0; i < m; ++i) {
            this.cnt[i] -= ans[i];
        }
        return ans;
    }
}

/**
 * Your ATM object will be instantiated and called as such:
 * var obj = new ATM()
 * obj.deposit(banknotesCount)
 * var param_2 = obj.withdraw(amount)
 */
```

<!-- tabs:end -->

<!-- solution:end -->

<!-- problem:end -->
