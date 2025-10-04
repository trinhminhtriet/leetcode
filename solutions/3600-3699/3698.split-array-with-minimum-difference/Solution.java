class Solution {
    public long splitArray(int[] nums) {
    
        if(nums.length==2){
            return Math.abs(nums[0]-nums[1]);
        }

        int left = 0;
        int right = nums.length-1;
        int ck = 0;
        long sum =0;
    
        
        for(int i=1;i<nums.length;i++){
            if(nums[i]<=nums[i-1]){
                break;
            }
            else{
                left=i;
            }
        }

        

        

        for(int j=nums.length-2;j>=0;j--){
            if(nums[j+1]>=nums[j]){
                break;
            }
            else{
                right=j;
            }
        }
        
      
        //ook now if both left and right are overlapping
        // then we have 2 answer's else 1 answer

        long ans =0;
        for(int num:nums){
            sum+=num;
        }
        int difff = Math.abs(left-right);
        if(difff>1){
            return -1;
        }
        
        if(left!=right){
            long cp =0;
            for(int d=0;d<=left;d++){
                cp+=nums[d];
            }
            long dif = sum-cp;
            ans=Math.abs(dif-cp);
        }
        else{
            long ap1 = 0;
            long ap2 = 0;

            long dd =0;
            for(int j=nums.length-1;j>=right+1;j--){
                dd+=nums[j];
            }
            long d1 = sum-dd;
            ap1= Math.abs(d1-dd);
long pp = 0; 
            for(int e=0;e<=left-1;e++){
             pp+=nums[e];   
            }
            long p1 = sum-pp;
            ap2 = Math.abs(p1-pp);
            ans= Math.min(ap1,ap2);
        }
        return ans;
    }
}