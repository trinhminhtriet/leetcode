class Solution {
    stack<vector<int>> num;
    stack<char> op;
    void eval() {
        auto b = num.top(); num.pop();
        auto a = num.top(); num.pop();
        char c = op.top(); op.pop();
        if (c == '&') {
            num.push({
                min({ a[0] + b[0], a[1] + b[0], a[0] + b[1] }),
                min({ a[1] + b[1], a[1] + b[0] + 1, a[0] + b[1] + 1, a[1] + b[1] + 1 })
            });
        } else {
            num.push({
                min({ a[0] + b[0], a[0] + b[1] + 1, a[1] + b[0] + 1, a[0] + b[0] + 1 }),
                min({ a[1] + b[1], a[1] + b[0], a[0] + b[1] })
            });
        }
    }
public:
    int minOperationsToFlip(string s) {
        for (char c : s) {
            if (isdigit(c)) {
                if (c == '0') num.push({0, 1});
                else num.push({1, 0});
            } else if (c == '(') {
                op.push(c);
            } else if (c == ')') {
                while (op.top() != '(') eval();
                op.pop();
            } else {
                while (op.size() && op.top() != '(') eval();
                op.push(c);
            }
        }
        while (op.size()) eval();
        return max(num.top()[0], num.top()[1]);
    }
};
