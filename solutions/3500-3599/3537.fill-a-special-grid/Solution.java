class Solution {
    public int[][] specialGrid(int n) {
        int size = 1 << n;
        int[][] grid = new int[size][size];
        solve(grid, 0, 0, size);
        return grid;
    }
    private int val = 0;
    private void solve(int[][] grid, int r, int c, int size){
        if (size == 1){
            grid[r][c] = val++;
            return;
        }
        int s = size / 2;
        solve(grid, r, c + s, s); // top right
        solve(grid, r + s, c + s, s); // bottom right
        solve(grid, r + s, c, s); // bottom left
        solve(grid, r, c, s); // top left
    }
}

