class Solution {
    public int minOperations(String s, int k) {

        long  n = s.length(), zero = 0;
        

        for(char c : s.toCharArray())
        {
           if( c == '0') zero++; 
        }
        long one = n - zero;
        int ans =-1;

        if( zero == 0) return 0;

        for(long i =1 ; i <=n ;i++)
        {
            long flip = i * (long)k;

            if((flip - zero) % 2 != 0) continue;

            if(i % 2 == 1)
            {
               if(flip >= zero && flip <= (zero * i + one * (i - 1)))
               {
                 return (int) i;
               }
            }
            else
            {
                if(flip >= zero && flip <= (zero * (i - 1) + one * i))
                {
                    return (int) i;
                }
            }
        }
        return ans;
    }
}