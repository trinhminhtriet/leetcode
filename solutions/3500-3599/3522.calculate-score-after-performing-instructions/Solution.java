class Solution {
    public long calculateScore(String[] instructions, int[] values) {
        int n = instructions.length;
        boolean[] visited = new boolean[n];
        long score = 0;
        int i = 0;

        while (i >= 0 && i < n && !visited[i]) {
            visited[i] = true;
            if (instructions[i].equals("add")) {
                score += values[i];
                i += 1;
            } else if (instructions[i].equals("jump")) {
                i += values[i];
            }
        }

        return score;
    }
}
