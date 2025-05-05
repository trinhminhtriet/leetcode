class Solution {
    public int maxProduct(int n) {
        ArrayList<Integer> digs = new ArrayList<>();
        while (n > 0) {
            digs.add(n % 10);
            n /= 10;
        }
        Collections.sort(digs);
        int size = digs.size();
        return digs.get(size - 1) * digs.get(size - 2);
    }
}

