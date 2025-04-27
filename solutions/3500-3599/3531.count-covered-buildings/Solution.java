import java.util.*;

class Solution {
    public int countCoveredBuildings(int n, int[][] grid) {
        Map<Integer, TreeSet<Integer>> st1 = new HashMap<>();
        Map<Integer, TreeSet<Integer>> st2 = new HashMap<>();

        for (int[] p : grid) {
            st1.computeIfAbsent(p[0], k -> new TreeSet<>()).add(p[1]);
            st2.computeIfAbsent(p[1], k -> new TreeSet<>()).add(p[0]);
        }

        int count = 0;
        for (int[] p : grid) {
            TreeSet<Integer> it1 = st1.get(p[0]);
            TreeSet<Integer> it2 = st2.get(p[1]);

            Integer lowerY = it1.lower(p[1]);
            Integer higherY = it1.higher(p[1]);
            Integer lowerX = it2.lower(p[0]);
            Integer higherX = it2.higher(p[0]);

            boolean up = lowerY != null;
            boolean down = higherY != null;
            boolean left = lowerX != null;
            boolean right = higherX != null;

            if (up && down && left && right) {
                count++;
            }
        }

        return count;
    }
}
