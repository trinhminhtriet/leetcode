INV = {
    1: 1,
    3: 7,
    7: 3,
    9: 9,
}


def mult(a, b):
    return (
        a[0] + b[0],
        a[1] + b[1],
        (a[2] * b[2]) % 10,
    )


def div(a, b):
    return (
        a[0] - b[0],
        a[1] - b[1],
        (a[2] * INV[b[2]]) % 10,
    )


def red(it):
    return sum((a * b) % 10 for a, b in it) % 10


TWOS = [2, 4, 8, 6]
FIVES = [5]


def simplify(tup):
    twos, fives, res = tup

    if twos:
        res = (res * TWOS[(twos - 1) % len(TWOS)]) % 10
    if fives:
        res = (res * FIVES[(fives - 1) % len(FIVES)]) % 10

    return res


class Solution:
    def hasSameDigits(self, s: str) -> bool:
        n = len(s) - 2

        facts = [(0, 0, 1)]
        while n >= len(facts):
            num = len(facts)

            twos = 0
            while num % 2 == 0:
                num //= 2
                twos += 1

            fives = 0
            while num % 5 == 0:
                num //= 5
                fives += 1

            facts.append(mult(facts[-1], (twos, fives, num)))

        pascal = []
        for k in range(n + 1):
            pascal.append(simplify(div(div(facts[n], facts[k]), facts[n - k])))

        a = red(zip(map(int, s), pascal))
        b = red(zip(map(int, s[1:]), pascal))

        return a == b
