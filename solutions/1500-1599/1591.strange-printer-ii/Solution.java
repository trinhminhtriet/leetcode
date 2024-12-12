class Solution {
    public boolean isPrintable(int[][] targetGrid) {
        Map<Integer, int[]> pos = new HashMap<>();
        int n = targetGrid.length;
        int m = targetGrid[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                pos.putIfAbsent(targetGrid[i][j], new int[]{n, m, -1, -1});
                int[] coord = pos.get(targetGrid[i][j]);
                coord[0] = Math.min(coord[0], i);
                coord[1] = Math.min(coord[1], j);
                coord[2] = Math.max(coord[2], i);
                coord[3] = Math.max(coord[3], j);
            }
        }
        Set<Integer> colors = new HashSet<>(pos.keySet());
        while (!colors.isEmpty()) {
            Set<Integer> next = new HashSet<>();
            for (int color : colors) {
                if (!erase(targetGrid, pos.get(color), color)) {
                    next.add(color);
                }
            }
            if (colors.size() == next.size()) {
                return false;
            }
            colors = next;
        }
        return true;
    }
    
    private boolean erase(int[][] targetGrid, int[] coord, int color) {
        for (int i = coord[0]; i <= coord[2]; i++) {
            for (int j = coord[1]; j <= coord[3]; j++) {
                if (targetGrid[i][j] > 0 && targetGrid[i][j] != color) {
                    return false;
                }
            }
        }
        for (int i = coord[0]; i <= coord[2]; i++) {
            for (int j = coord[1]; j <= coord[3]; j++) {
                targetGrid[i][j] = 0;
            }
        }
        return true;
    }
}