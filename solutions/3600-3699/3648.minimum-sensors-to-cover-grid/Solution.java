class Solution {
    public int minSensors(int n, int m, int k) {
        int size = k * 2 + 1;
        int x = n / size + (n % size == 0 ? 0 : 1);
        int y = m / size + (m % size == 0 ? 0 : 1);
        return x * y;
    }
}