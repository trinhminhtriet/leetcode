	class Solution {
		static long[][] fts = new long[70001][];
		static {
			for (int i = 1; i <= 70000; i++) {
				fts[i] = new long[70000 / i + 4];
			}
		}

		long solve(List<Integer> a, int lim)
		{
			if(a.size() <= 0)return 0;
			long[] ft = new long[lim+3];
			for(int v : a){
				addFenwick(ft, v, sumFenwick(ft, v-1) + 1);
			}
			return sumFenwick(ft, lim);
		}

		public static final int mod = 1000000007;

		public static long sumFenwick(long[] ft, int i)
		{
			long sum = 0;
			for(i++;i > 0;i -= i&-i){
				sum += ft[i];
				if(sum >= mod)sum -= mod;
			}
			return sum;
		}

		public static void addFenwick(long[] ft, int i, long v)
		{
			v %= mod;
			if(v < 0)v += mod;
			if(v == 0)return;
			int n = ft.length;
			for(i++;i < n;i += i&-i){
				ft[i] += v;
				if(ft[i] >= mod)ft[i] -= mod;
			}
		}

		public int totalBeauty(int[] nums) {
			for(int i = 1;i <= 70000;i++){
				Arrays.fill(fts[i], 0);
			}
			for(int v : nums){
				for(int d = 1;d*d <= v;d++){
					if(v % d == 0){
						addFenwick(fts[d], v/d, sumFenwick(fts[d], v/d-1) + 1);
						if(d * d != v){
							addFenwick(fts[v/d], v/(v/d), sumFenwick(fts[v/d], v/(v/d)-1) + 1);
						}
					}
				}
			}
			long[] res = new long[70001];
			for(int i = 1;i <= 70000;i++){
				res[i] = sumFenwick(fts[i], 70000/i+1);
			}
			for(int i = 70000;i >= 1;i--){
				for(int j = i*2;j <= 70000;j += i){
					res[i] -= res[j];
					if(res[i] < 0)res[i] += mod;
				}
			}
			long ans = 0;
			for(int i = 1;i <= 70000;i++){
				ans += i * res[i];
				ans %= mod;
			}
			return (int)ans;
		}
	}