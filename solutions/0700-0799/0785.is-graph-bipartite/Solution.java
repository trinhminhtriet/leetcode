class Solution {
    private int[] colors;
    private int[][] graph;

    public boolean isBipartite(int[][] graph) {
        int numNodes = graph.length;
        colors = new int[numNodes];
        this.graph = graph;

        for (int node = 0; node < numNodes; ++node) {
            if (colors[node] == 0 && !depthFirstSearch(node, 1)) {
                return false;
            }
        }
        return true;
    }

    private boolean depthFirstSearch(int node, int color) {
        colors[node] = color;
        for (int adjacent : graph[node]) {
            if (colors[adjacent] == 0) {
                if (!depthFirstSearch(adjacent, 3 - color)) {
                    return false;
                }
            } else if (colors[adjacent] == color) {
                return false;
            }
        }
        return true;
    }
}
