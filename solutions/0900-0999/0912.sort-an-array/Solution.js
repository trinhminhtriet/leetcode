/**
 * @param {number[]} nums
 * @return {number[]}
 */
var sortArray = function (nums) {
    function quickSort(l, r) {
        if (l >= r) {
            return;
        }
        let i = l - 1;
        let j = r + 1;
        const x = nums[(l + r) >> 1];
        while (i < j) {
            while (nums[++i] < x);
            while (nums[--j] > x);
            if (i < j) {
                [nums[i], nums[j]] = [nums[j], nums[i]];
            }
        }
        quickSort(l, j);
        quickSort(j + 1, r);
    }
    const n = nums.length;
    quickSort(0, n - 1);
    return nums;
};
