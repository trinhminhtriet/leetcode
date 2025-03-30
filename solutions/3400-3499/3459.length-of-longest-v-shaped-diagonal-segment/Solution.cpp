class Solution {
    public:
        int dp[501][501][2][2][5];
        int n , m;
        int solve(int r , int c , int turn , int curr , int dir , vector<vector<int>>& grid){
            if(r == n || c == m || r < 0 || c < 0)return 0;
            if(dp[r][c][turn][curr][dir] != -1)return dp[r][c][turn][curr][dir];
            if(curr && grid[r][c] != 2)return 0;
            if(!curr && grid[r][c] != 0)return 0;
            int ans = 0;
            // 1 - top right , 2 - bottom right , 3 - bottom left , 4 - top left
            if(dir == 1){
                ans = 1 + solve(r - 1 , c + 1 , turn , !curr , 1 , grid);
                if(!turn)ans = max(ans ,1 + solve(r + 1 , c + 1 , 1 , !curr , 2 , grid));
            }
            if(dir == 2){
                ans = 1 + solve(r + 1 , c + 1 , turn , !curr , 2 , grid);
                if(!turn)ans = max(ans ,1 + solve(r + 1 , c - 1 , 1 , !curr , 3 , grid));
            }
            if(dir == 3){
                ans = 1 + solve(r + 1 , c - 1 , turn , !curr , 3 , grid);
                if(!turn)ans = max(ans ,1 + solve(r - 1 , c - 1 , 1 , !curr , 4 , grid));
            }
            if(dir == 4){
                ans = 1 + solve(r - 1 , c - 1 , turn , !curr , 4 , grid);
                if(!turn)ans = max(ans ,1 + solve(r - 1 , c + 1 , 1 , !curr , 1 , grid));
            }
            return dp[r][c][turn][curr][dir] = ans;
        }
        int lenOfVDiagonal(vector<vector<int>>& grid) {
            n = grid.size() , m = grid[0].size();
            memset(dp , -1 ,sizeof(dp));
            int ans = 0;
            for(int i = 0 ; i < n ; i++){
                for(int j = 0 ; j < m ; j++){
                    if(grid[i][j] == 1){
                        ans = max(ans , 1+ solve(i + 1 , j + 1 , 0 , 1 , 2 , grid));
                        ans = max(ans , 1+ solve(i - 1 , j + 1 , 0 , 1 , 1 , grid));
                        ans = max(ans , 1+ solve(i + 1 , j - 1 , 0 , 1 , 3 , grid));
                        ans = max(ans , 1+ solve(i - 1 , j - 1 , 0 , 1 , 4 , grid));
                    }
                }
            }
            return ans;
        }
    };
