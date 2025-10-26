# Solution: Simulation

According to the problem description, we can use an array `balance` to simulate the balance of bank accounts. The array index starts from 0, and the value of the array represents the balance of the account.

-   During initialization, we assign the `balance` array to the member variable `this.balance`, and assign the length of `balance` to the member variable `this.n`.
-   In the `transfer` function, if `account1` or `account2` is greater than `n` or `balance[account1 - 1]` is less than `money`, return `false`. Otherwise, subtract `money` from `balance[account1 - 1]`, add `money` to `balance[account2 - 1]`, and return `true`.
-   In the `deposit` function, if `account` is greater than `n`, return `false`. Otherwise, add `money` to `balance[account - 1]`, and return `true`.
-   In the `withdraw` function, if `account` is greater than `n` or `balance[account - 1]` is less than `money`, return `false`. Otherwise, subtract `money` from `balance[account - 1]`, and return `true`.

The time complexity of the above operations is $O(1)$, and the space complexity is $O(n)$. Here, $n$ is the length of `balance`.

## Code


#### Python3

```python
class Bank:
    def __init__(self, balance: List[int]):
        self.balance = balance
        self.n = len(balance)

    def transfer(self, account1: int, account2: int, money: int) -> bool:
        if account1 > self.n or account2 > self.n or self.balance[account1 - 1] < money:
            return False
        self.balance[account1 - 1] -= money
        self.balance[account2 - 1] += money
        return True

    def deposit(self, account: int, money: int) -> bool:
        if account > self.n:
            return False
        self.balance[account - 1] += money
        return True

    def withdraw(self, account: int, money: int) -> bool:
        if account > self.n or self.balance[account - 1] < money:
            return False
        self.balance[account - 1] -= money
        return True


# Your Bank object will be instantiated and called as such:
# obj = Bank(balance)
# param_1 = obj.transfer(account1,account2,money)
# param_2 = obj.deposit(account,money)
# param_3 = obj.withdraw(account,money)
```

#### Java

```java
class Bank {
    private long[] balance;
    private int n;

    public Bank(long[] balance) {
        this.balance = balance;
        this.n = balance.length;
    }

    public boolean transfer(int account1, int account2, long money) {
        if (account1 > n || account2 > n || balance[account1 - 1] < money) {
            return false;
        }
        balance[account1 - 1] -= money;
        balance[account2 - 1] += money;
        return true;
    }

    public boolean deposit(int account, long money) {
        if (account > n) {
            return false;
        }
        balance[account - 1] += money;
        return true;
    }

    public boolean withdraw(int account, long money) {
        if (account > n || balance[account - 1] < money) {
            return false;
        }
        balance[account - 1] -= money;
        return true;
    }
}

/**
 * Your Bank object will be instantiated and called as such:
 * Bank obj = new Bank(balance);
 * boolean param_1 = obj.transfer(account1,account2,money);
 * boolean param_2 = obj.deposit(account,money);
 * boolean param_3 = obj.withdraw(account,money);
 */
```

#### C++

```cpp
class Bank {
public:
    vector<long long> balance;
    int n;

    Bank(vector<long long>& balance) {
        this->balance = balance;
        n = balance.size();
    }

    bool transfer(int account1, int account2, long long money) {
        if (account1 > n || account2 > n || balance[account1 - 1] < money) return false;
        balance[account1 - 1] -= money;
        balance[account2 - 1] += money;
        return true;
    }

    bool deposit(int account, long long money) {
        if (account > n) return false;
        balance[account - 1] += money;
        return true;
    }

    bool withdraw(int account, long long money) {
        if (account > n || balance[account - 1] < money) return false;
        balance[account - 1] -= money;
        return true;
    }
};

/**
 * Your Bank object will be instantiated and called as such:
 * Bank* obj = new Bank(balance);
 * bool param_1 = obj->transfer(account1,account2,money);
 * bool param_2 = obj->deposit(account,money);
 * bool param_3 = obj->withdraw(account,money);
 */
```

#### Go

```go
type Bank struct {
	balance []int64
	n       int
}

func Constructor(balance []int64) Bank {
	return Bank{balance, len(balance)}
}

func (this *Bank) Transfer(account1 int, account2 int, money int64) bool {
	if account1 > this.n || account2 > this.n || this.balance[account1-1] < money {
		return false
	}
	this.balance[account1-1] -= money
	this.balance[account2-1] += money
	return true
}

func (this *Bank) Deposit(account int, money int64) bool {
	if account > this.n {
		return false
	}
	this.balance[account-1] += money
	return true
}

func (this *Bank) Withdraw(account int, money int64) bool {
	if account > this.n || this.balance[account-1] < money {
		return false
	}
	this.balance[account-1] -= money
	return true
}

/**
 * Your Bank object will be instantiated and called as such:
 * obj := Constructor(balance);
 * param_1 := obj.Transfer(account1,account2,money);
 * param_2 := obj.Deposit(account,money);
 * param_3 := obj.Withdraw(account,money);
 */
```

#### TypeScript

```ts
class Bank {
    balance: number[];
    constructor(balance: number[]) {
        this.balance = balance;
    }

    transfer(account1: number, account2: number, money: number): boolean {
        if (
            account1 > this.balance.length ||
            account2 > this.balance.length ||
            money > this.balance[account1 - 1]
        )
            return false;
        this.balance[account1 - 1] -= money;
        this.balance[account2 - 1] += money;
        return true;
    }

    deposit(account: number, money: number): boolean {
        if (account > this.balance.length) return false;
        this.balance[account - 1] += money;
        return true;
    }

    withdraw(account: number, money: number): boolean {
        if (account > this.balance.length || money > this.balance[account - 1]) {
            return false;
        }
        this.balance[account - 1] -= money;
        return true;
    }
}

/**
 * Your Bank object will be instantiated and called as such:
 * var obj = new Bank(balance)
 * var param_1 = obj.transfer(account1,account2,money)
 * var param_2 = obj.deposit(account,money)
 * var param_3 = obj.withdraw(account,money)
 */
```

#### Rust

```rust
struct Bank {
    balance: Vec<i64>,
}

/**
 * `&self` means the method takes an immutable reference.
 * If you need a mutable reference, change it to `&mut self` instead.
 */
impl Bank {
    fn new(balance: Vec<i64>) -> Self {
        Bank { balance }
    }

    fn transfer(&mut self, account1: i32, account2: i32, money: i64) -> bool {
        let (account1, account2, n) = (account1 as usize, account2 as usize, self.balance.len());
        if n < account1 || n < account2 {
            return false;
        }
        if self.balance[account1 - 1] < money {
            return false;
        }
        self.balance[account1 - 1] -= money;
        self.balance[account2 - 1] += money;
        true
    }

    fn deposit(&mut self, account: i32, money: i64) -> bool {
        let (account, n) = (account as usize, self.balance.len());
        if n < account {
            return false;
        }
        self.balance[account - 1] += money;
        true
    }

    fn withdraw(&mut self, account: i32, money: i64) -> bool {
        let (account, n) = (account as usize, self.balance.len());
        if n < account {
            return false;
        }
        if self.balance[account - 1] < money {
            return false;
        }
        self.balance[account - 1] -= money;
        true
    }
}
```


## Contact information

If you have any questions or suggestions, please feel free to contact me at https://trinhminhtriet.com/
- Website: [trinhminhtriet.com](https://trinhminhtriet.com/)
- Github: [trinhminhtriet](https://github.com/trinhminhtriet)
- LinkedIn: [trinhminhtriet](https://www.linkedin.com/in/triet-trinh/)
- Twitter: [trinhminhtriet](https://twitter.com/trinhminhtriet)
- Email: [contact@trinhminhtriet.com](mailto:contact@trinhminhtriet.com)
