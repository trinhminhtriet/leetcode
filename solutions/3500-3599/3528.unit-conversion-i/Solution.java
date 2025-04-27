import java.util.*;

class Solution {
    public int[] baseUnitConversions(int[][] conversions) {
        int n = conversions.length + 1;
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] conv : conversions) {
            graph.get(conv[0]).add(new int[]{conv[1], conv[2]});
        }
        
        int[] ans = new int[n];
        final int mod = (int)1e9 + 7;
        Queue<Integer> queue = new LinkedList<>();
        
        ans[0] = 1;
        queue.offer(0);
        
        while (!queue.isEmpty()) {
            int x = queue.poll();
            for (int[] edge : graph.get(x)) {
                int y = edge[0];
                int z = edge[1];
                ans[y] = (int)(((long) ans[x] * z) % mod);
                queue.offer(y);
            }
        }
        
        return ans;
    }
}

