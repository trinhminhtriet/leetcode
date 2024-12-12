function uncommonFromSentences(s1: string, s2: string): string[] {
    const cnt: Map<string, number> = new Map();
    for (const s of [...s1.split(' '), ...s2.split(' ')]) {
        cnt.set(s, (cnt.get(s) || 0) + 1);
    }
    const ans: Array<string> = [];
    for (const [s, v] of cnt.entries()) {
        if (v == 1) {
            ans.push(s);
        }
    }
    return ans;
}
