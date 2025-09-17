class Solution {
    public int minCost(int[][] grid, int k) {
        int m=grid.length;
        int n=grid[0].length;
        int[][][]dp=new int[m+1][n+1][k+1];

        for(int i=0;i<=m;i++){
            for(int j=0;j<=n;j++){
                for(int l=0;l<=k;l++){
                    dp[i][j][l]=(int)1e9;
                }
            }
        }

        int N=(int)1e4;
        int[]pbest=new int[N+1];
        Arrays.fill(pbest,(int)1e9);

        for(int l=0;l<=k;l++){
            int[]currbest=new int[N+1];
            Arrays.fill(currbest,(int)1e9);
            for(int i=m-1;i>=0;i--){
                for(int j=n-1;j>=0;j--){
                
                    // base case
                    if(i==m-1 && j==n-1){
                        dp[i][j][l]=0;
                        
                    }

                    // transitions
                    else{
                        int mini=(int)1e9;
                        if(i+1<=m-1) mini=Math.min(mini,grid[i+1][j]+dp[i+1][j][l]);
                        if(j+1<=n-1) mini=Math.min(mini,grid[i][j+1]+dp[i][j+1][l]);
                        if(l>0){
                            mini=Math.min(mini,0+pbest[grid[i][j]] );
                        }
                        dp[i][j][l]=mini;
                    }
                    currbest[grid[i][j]]=Math.min(currbest[grid[i][j]],dp[i][j][l]);


                }
            }

          
            pbest=new int[N+1];
            Arrays.fill(pbest,(int)1e9);
            pbest[0]=currbest[0];
            
            for(int i=1;i<=N;i++){
                pbest[i]=Math.min(pbest[i-1],currbest[i]);
            }

        }

        return dp[0][0][k];

    }
}