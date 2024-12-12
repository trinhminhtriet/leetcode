/**
 * @param {character[][]} grid
 * @return {boolean}
 */
const hasValidPath = function (grid) {
    const m = grid.length
    const n = grid[0].length
    if (grid[0][0] != '(' || grid[m - 1][n - 1] != ')') return false
    const dp = kdArr(-1, [m, n, ~~((m + n) / 2) + 1])
  
  
    function dfs(i, j, left) {
      if (i >= m || j >= n) return false
      if (grid[i][j] === '(') left++
      else left--
      if (left < 0 || left > Math.floor((m + n) / 2)) return false
      if (dp[i][j][left] != -1) return dp[i][j][left]
      if (i == m - 1 && j == n - 1 && left == 0) return (dp[i][j][left] = true)
      return (dp[i][j][left] = dfs(i, j + 1, left) || dfs(i + 1, j, left))
    }
    return dfs(0, 0, 0)
    
    function kdArr(defaultVal, arr) {
      if(arr.length === 1) return Array(arr[0]).fill(defaultVal)
      
      const res = []
      for(let i = 0, len = arr[0]; i < len; i++) {
        res.push(kdArr(defaultVal, arr.slice(1)))
      }
      
      return res
    }
  }