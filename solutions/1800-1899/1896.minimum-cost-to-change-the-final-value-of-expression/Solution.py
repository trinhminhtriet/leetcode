from typing import List

class Solution:
    def minOperationsToFlip(self, expression: str) -> int:
        stack = []
        for char in expression:
            if char in ('0', '1', '|', '&'):
                stack.append((char, 1 if char in '01' else 0))
            elif char == '(':
                stack.append(char)
            else:
                e2 = stack.pop()
                stack.pop()
                e1 = stack.pop()
                if e2[0] == '0' and e1[0] == '|':
                    stack.append(('0', 1))
                else:
                    stack.append((max(e1, e2, key=lambda x: (x[0] == '1', -x[1]))))
        while len(stack) > 1:
            e2 = stack.pop()
            op = stack.pop()
            e1 = stack.pop()
            if e2[0] == e1[0]:
                stack.append((e2[0], 1 + min(e1[1], e2[1])))
            else:
                stack.append((e2[0] if op[0] == '|' else e1[0], max(e1[1], e2[1])))
        return stack[0][1]