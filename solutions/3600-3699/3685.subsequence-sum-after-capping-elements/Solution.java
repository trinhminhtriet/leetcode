class Solution {
    public boolean[] subsequenceSumAfterCapping(int[] a, int k) {
        Arrays.sort(a);
		int n = a.length;
		
		boolean[][] dp = new boolean[n + 1][4001];
		boolean[] ans = new boolean[n];

        if(k==1 && n==1){
            ans[0] = true; 
            return ans;
        }

		for (int i = 0; i < n; i++) {
			dp[i][0] = true;
		}

		for (int i = 0; i < n; i++) {

			dp[i][a[i]] = true;

			for (int j = 1; j <= k; j++) {
				if (i - 1 >= 0) {
					dp[i][j] |= dp[i - 1][j];

					if (j - a[i] >= 0) {
						dp[i][j] |= dp[i - 1][j - a[i]];
					}
				}
			}
		}


		for (int i = 1; i <= n; i++) {

			int ind = greaterEle(a, i);
			int cnt = n - ind;
			
			for (int j = 0; j <= cnt; j++) {
				int x = j * i;

				if (x > k) {
					break;
				}
				if(x==k) {
					ans[i-1] = true; 
					break; 
				}
				if(ind-1 >= 0) {
					if (dp[ind-1][k - x]) {
						ans[i - 1] = true;
						break;
					}
				}
			}
		}

        return ans;
    }

    private int greaterEle(int[] arr, int x) {
		int index = Arrays.binarySearch(arr, x);

		if (index < 0) {
			index = -index - 1;
		} else {
			while (index < arr.length && arr[index] == x) {
				index++;
			}
		}

		return index;
	}
}