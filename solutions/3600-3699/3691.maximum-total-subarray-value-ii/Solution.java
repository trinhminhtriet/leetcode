class Solution {
    public int [][] createsparse(int [] nums, int n, boolean mintable){
        int maxlog = 32 - Integer.numberOfLeadingZeros(n);//like takinf floor(log(n))
        int [][] stable = new int[n][maxlog+1];
        for(int i = 0; i < n; i++) stable[i][0] = i;//same index for that
        
        for(int j = 1; (1 << j) <= n; j++){
            //(1 << j) is just 2 ^ j fast using bit manipulation
            for(int i = 0; i + (1 << j) <= n; i++){
                 //(i + 2 ^ k) < n
                 if (mintable) {
    stable[i][j] = nums[stable[i][j-1]] < nums[stable[i + (1 << (j-1))][j-1]] 
                   ? stable[i][j-1] 
                   : stable[i + (1 << (j-1))][j-1];
} else {
    stable[i][j] = nums[stable[i][j-1]] > nums[stable[i + (1 << (j-1))][j-1]] 
                   ? stable[i][j-1] 
                   : stable[i + (1 << (j-1))][j-1];
}
            }
        }
        return stable;
    }
    int rminq(int lo, int hi, int [] nums, int [][] stable){
        int len = hi-lo+1;//num elment in window
        int k = 31 - Integer.numberOfLeadingZeros(len);
        return Math.min(nums[stable[lo][k]], nums[stable[hi - (1 << k) + 1][k]]);
    }
    int rmaxq(int lo, int hi, int [] nums, int [][] stable){
        int len = hi-lo+1;//num elment in window
        int k = 31 - Integer.numberOfLeadingZeros(len);
        return Math.max(nums[stable[lo][k]], nums[stable[hi - (1 <<k) + 1][k]]);
    }
    public long maxTotalValue(int[] nums, int k) {
         int n = nums.length;
         int [][] minstable = createsparse(nums, nums.length, true);//crate for minimum query firt
         int [][] maxstable = createsparse(nums, nums.length, false);//craete for max query now
          //int minel = rmaxq(0, 2, nums, maxstable);
          PriorityQueue<int []> pq = new PriorityQueue<>(
            (a,b) -> b[0] - a[0]// b is big so come later , as b-a > 0
          );
           for(int l = 0; l < n; l++){
                   int maxel = rmaxq(l , n-1 , nums, maxstable);
                   int minel = rminq(l,  n-1 , nums, minstable);
                   int val = maxel - minel;
                   pq.offer(new int[]{val,l,n-1});
           }//for all sub arrays starting at l 
          long ans = 0;
          for(int i = 0; i < k; i++){
             int [] cur = pq.poll();
             int val = cur[0], l = cur[1], r = cur[2];
             ans += val;//add value to anwer
             int newr = r-1;
             if(newr >= l){
                //must be valid sub arr
                int newval = rmaxq(l,newr,nums,maxstable) - rminq(l,newr,nums,minstable);
                pq.offer(new int[]{newval,l,newr});//for r values
             }
          }
          return ans;
    }
}