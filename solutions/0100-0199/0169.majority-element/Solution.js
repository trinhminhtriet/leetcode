/**
 * @param {number[]} nums
 * @return {number}
 */
var majorityElement = function (nums) {
    let cnt = 0;
    let m = 0;
    for (const x of nums) {
        if (cnt === 0) {
            m = x;
            cnt = 1;
        } else {
            cnt += m === x ? 1 : -1;
        }
    }
    return m;
};
