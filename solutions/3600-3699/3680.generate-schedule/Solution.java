import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

class Solution {
    public int[][] generateSchedule(int n) {
        if (n <= 4) {
            return new int[0][0]; // No solution exists for n <= 4.
        }

        List<int[]> all_pairs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    all_pairs.add(new int[]{i, j});
                }
            }
        }
        
        List<int[]> ans = new LinkedList<>();
        ans.add(new int[]{-1, -2}); // Add a dummy pair at the beginning.
        ans.add(new int[]{-3, -4}); // Add a dummy pair at the end.

        while (!all_pairs.isEmpty()) {
            List<int[]> temp = new ArrayList<>(); // Holds pairs that couldn't be placed in this pass.

            for (int[] pair : all_pairs) {
                int x = pair[0];
                int y = pair[1];
                boolean inserted = false;

                for (int j = 0; j < ans.size() - 1; j++) {
                    if (allow_at_index(j, x, y, ans)) {
                        ans.add(j + 1, pair); // Insert the pair at a valid spot.
                        inserted = true;
                        break;
                    }
                }
                
                if (inserted == false) {
                    temp.add(pair); // If not inserted, carry over for the next iteration.
                }
            }
            
            all_pairs = temp; // Update the list of pairs for the next pass.
        }

        ans.remove(0); // Remove the first dummy pair.
        ans.remove(ans.size() - 1); // Remove the last dummy pair.

        return ans.toArray(new int[0][]); // Convert list to 2D array and return.
    }

    // Helper method to check if a pair (x,y) can be inserted at index i
    private boolean allow_at_index(int i, int x, int y, List<int[]> ans) {
        int x1 = ans.get(i)[0];
        int y1 = ans.get(i)[1];
        int x2 = ans.get(i + 1)[0];
        int y2 = ans.get(i + 1)[1];

        // Check if the 6 people are all distinct using a Set.
        Set<Integer> distinctNumbers = new HashSet<>(Arrays.asList(x, y, x1, y1, x2, y2));
        
        return distinctNumbers.size() == 6;
    }
}