import java.util.*;

class Solution {
    public long maxArea(int[][] coords) {
        int n = coords.length;

        Map<Integer, TreeSet<Integer>> xMap = new HashMap<>();
        Map<Integer, TreeSet<Integer>> yMap = new HashMap<>();
        TreeSet<Integer> allX = new TreeSet<>();
        TreeSet<Integer> allY = new TreeSet<>();

        for (int[] coord : coords) {
            int x = coord[0], y = coord[1];
            xMap.computeIfAbsent(x, k -> new TreeSet<>()).add(y);
            yMap.computeIfAbsent(y, k -> new TreeSet<>()).add(x);
            allX.add(x);
            allY.add(y);
        }

        long ans = Long.MIN_VALUE;

        for (Map.Entry<Integer, TreeSet<Integer>> entry : xMap.entrySet()) {
            int x = entry.getKey();
            TreeSet<Integer> ySet = entry.getValue();
            if (ySet.size() < 2)
                continue;

            int minY = ySet.first();
            int maxY = ySet.last();
            int base = maxY - minY;

            int minX = allX.first();
            int maxX = allX.last();

            if (minX != x)
                ans = Math.max(ans, 1L * Math.abs(x - minX) * base);
            if (maxX != x)
                ans = Math.max(ans, 1L * Math.abs(x - maxX) * base);
        }

        for (Map.Entry<Integer, TreeSet<Integer>> entry : yMap.entrySet()) {
            int y = entry.getKey();
            TreeSet<Integer> xSet = entry.getValue();
            if (xSet.size() < 2)
                continue;

            int minX = xSet.first();
            int maxX = xSet.last();
            int base = maxX - minX;

            int minY = allY.first();
            int maxY = allY.last();

            if (minY != y)
                ans = Math.max(ans, 1L * Math.abs(y - minY) * base);
            if (maxY != y)
                ans = Math.max(ans, 1L * Math.abs(y - maxY) * base);
        }

        return ans == Long.MIN_VALUE ? -1 : ans;
    }
}
