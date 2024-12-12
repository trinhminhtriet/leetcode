/**
 * @param {string} s
 * @param {number} k
 * @return {string}
 */
const longestSubsequenceRepeatedK = function (s, k) {
    const n = s.length, a = 'a'.charCodeAt(0)

    let res = ''
    const q = ['']

    while (q.length) {
        const size = q.length
        for (let i = 0; i < size; i++) {
            const cur = q.shift()
            for (let j = 0; j < 26; j++) {
                const next = cur + String.fromCharCode(a + j)
                if (isSub(s, next, k)) {
                    res = next
                    q.push(next)
                }
            }

        }
    }

    return res


    function isSub(s, p, k) {
        let repeated = 0
        for (let i = 0, j = 0, n = s.length, m = p.length; i < n; i++) {
            if (s[i] === p[j]) {
                j++
                if (j === m) {
                    repeated++
                    j = 0
                    if (repeated === k) {
                        return true
                    }
                }
            }
        }

        return false
    }
};