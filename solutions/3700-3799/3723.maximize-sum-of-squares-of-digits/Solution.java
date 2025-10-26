class Solution {
    public String maxSumOfSquares(int num, int sum) {
        if(sum > 9 * num) return "";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < num; i++) {
            int d = Math.min(9, sum);
            sb.append(d);
            sum -= d;
        }
        if(sum != 0) return "";
        return sb.toString();
    }
}