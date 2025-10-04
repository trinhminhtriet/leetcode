class Solution {
    public int countStableSubsequences(int[] nums) {
      long prevOdd1=0,prevOdd2=0,prevEven1=0,prevEven2=0;
      for(int i=0;i<nums.length;i++)
      {
         long odd1=0,odd2=0,even1=0,even2=0;
         if(nums[i]%2==0)
         {
             odd1=prevOdd1;
             odd2=prevOdd2;
             even1=prevOdd1+prevOdd2+prevEven1+1;
             even2=prevEven2+prevEven1;
         }
         else
         {
            odd1=1+prevOdd1+prevEven1+prevEven2;
            odd2=prevOdd1+prevOdd2;
            even1=prevEven1;
            even2=prevEven2;
         }

         prevOdd1=(odd1 % 1000000007);
         prevOdd2=(odd2 % 1000000007);
         prevEven1=(even1 % 1000000007);
         prevEven2=(even2 % 1000000007);
      }
      return (int)((prevOdd1+prevOdd2+prevEven1+prevEven2) % 1000000007);

    }
}