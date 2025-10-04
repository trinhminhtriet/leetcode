class Solution {
    public int uniquePaths(int[][] grid) {
        long arr[][] = new long[grid.length][grid[0].length];
        for (int i = grid.length - 1; i >= 0; i--) {
            for (int j = grid[0].length - 1; j >= 0; j--) {
                if (i == grid.length - 1 && j == grid[0].length - 1) {
                    arr[i][j] = 1l;
                } else {
                    if (i == grid.length - 1) {
                        if (grid[i][j] == 1) {
                            arr[i][j] = 0;
                        } else {
                            arr[i][j] = arr[i][j + 1];
                        }
                    } else if (j == grid[0].length - 1) {
                        if (grid[i][j] == 1) {
                            arr[i][j] = 0;
                        } else {
                            arr[i][j] = arr[i + 1][j];
                        }
                    } else {
                        if (grid[i][j] == 1) {
                            continue;
                        }
                        long ans = 0;
                        if (grid[i + 1][j] == 1) {
                            int x = i + 1, y = j;
                            int flag = 0;
                            while (true) {
                                if (flag == 0) {
                                    y = y + 1;
                                    if (y < grid[0].length) {
                                        if (grid[x][y] == 1) {
                                            flag = 1;
                                        } else {
                                            ans = arr[x][y];
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                } else {
                                    x = x + 1;
                                    if (x < grid.length) {
                                        if (grid[x][y] == 1) {
                                            flag = 0;
                                        } else {
                                            ans = arr[x][y];
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            }
                        } else {
                            ans = arr[i + 1][j];
                        }
                        if (grid[i][j + 1] == 1) {
                            int x = i, y = j + 1;
                            int flag = 0;
                            while (true) {
                                if (flag == 1) {
                                    y = y + 1;
                                    if (y < grid[0].length) {
                                        if (grid[x][y] == 1) {
                                            flag = 0;
                                        } else {
                                            ans += arr[x][y];
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                } else {
                                    x = x + 1;
                                    if (x < grid.length) {
                                        if (grid[x][y] == 1) {
                                            flag = 1;
                                        } else {
                                            ans += arr[x][y];
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            }
                        } else {
                            ans += arr[i][j + 1];
                        }

                        arr[i][j] = ans%1000000007;
                    }
                }
            }
        }

        return (int)arr[0][0];
    }
}