class Solution {
    long f(long h, int l){
        long rs=h;
        long o=h;
        if(l%2==1) o>>=1;
        while(o>0){
            rs=(rs<<1)|(o&1);
            o>>=1;
        }
        return rs;
    }
    public int countBinaryPalindromes(long n) {
        int rs=0;
        int mx=(int) 64-Long.numberOfLeadingZeros(n);
        for(int i=1; i<=mx; i++){
            int h=(i+1)/2;
            long s=1L<<(h-1), e=(1L<<h)-1;
            long l=f(s, i), r=f(e, i);
            if(l>n) break;
            if(r<=n) rs+=(1<<(h-1));
            else{
                long a=s, b=e, z=s;
                while(a<=b){
                    long m=a+(b-a)/2;
                    long p=f(m, i);
                    if(p<=n){
                        z=m;
                        a=m+1;
                    }
                    else b=m-1;
                }
                rs+=(z-s+1);
            }
        }
        return rs+1;
    }
}
// for if: 1<<(h-1): caus: for h bits, poss are: 1*(2*2*2...*2 .,h-1 times) = 1<<(h-1) times as in any half start bit must be 1