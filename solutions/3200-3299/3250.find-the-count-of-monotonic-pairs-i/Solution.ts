function countOfPairs(nums: number[]): number {
    const mod = 1e9 + 7;
    const n = nums.length;
    const m = Math.max(...nums);
    const f: number[][] = Array.from({ length: n }, () => Array(m + 1).fill(0));
    for (let j = 0; j <= nums[0]; j++) {
        f[0][j] = 1;
    }
    const g: number[] = Array(m + 1).fill(0);
    for (let i = 1; i < n; i++) {
        g[0] = f[i - 1][0];
        for (let j = 1; j <= m; j++) {
            g[j] = (g[j - 1] + f[i - 1][j]) % mod;
        }
        for (let j = 0; j <= nums[i]; j++) {
            const k = Math.min(j, j + nums[i - 1] - nums[i]);
            if (k >= 0) {
                f[i][j] = g[k];
            }
        }
    }
    let ans = 0;
    for (let j = 0; j <= nums[n - 1]; j++) {
        ans = (ans + f[n - 1][j]) % mod;
    }
    return ans;
}