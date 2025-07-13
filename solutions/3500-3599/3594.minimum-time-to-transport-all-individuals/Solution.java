import java.util.*;

class Solution {

    class State {
        int mask, stage, side;
        double cost;

        State(int mask, int stage, int side, double cost) {
            this.mask = mask;
            this.stage = stage;
            this.side = side;
            this.cost = cost;
        }
    }

    // Count number of set bits (people selected)
    int countBits(int x) {
        int count = 0;
        while (x > 0) {
            count += x & 1;
            x >>= 1;
        }
        return count;
    }

    // Get max time among selected people (those whose bit is set in val)
    int calcMaxTime(int[] time, int val) {
        int max = Integer.MIN_VALUE;
        int i = 0;
        while (val > 0) {
            if ((val & 1) == 1)
                max = Math.max(max, time[i]);
            val >>= 1;
            i++;
        }
        return max;
    }

    public double minTime(int n, int k, int m, int[] time, double[] mul) {
        int maxMask = 1 << n;

        // Edge case: impossible if only 1 person can go at a time and n > 1
        if (k == 1 && n > 1)
            return -1;

        // Visited[mask][stage][side] to mark if we have processed a state
        boolean[][][] visited = new boolean[maxMask][m][2];

        // Precompute all possible valid groups of people with at most k members
        List<int[]> validGroups = new ArrayList<>();
        for (int i = 1; i < maxMask; i++) {
            if (countBits(i) <= k) {
                validGroups.add(new int[] { i, calcMaxTime(time, i) });
            }
        }

        // Min-heap: (total_time_so_far, mask, stage, side)
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingDouble(s -> s.cost));
        pq.offer(new State(0, 0, 0, 0.0)); // Start from empty camp (mask=0), stage 0, boat on camp side

        while (!pq.isEmpty()) {
            State curr = pq.poll();

            if (curr.mask == (1 << n) - 1)
                return curr.cost; // All people transferred

            if (visited[curr.mask][curr.stage][curr.side])
                continue;
            visited[curr.mask][curr.stage][curr.side] = true;

            if (curr.side == 0) {
                // Boat is at camp → send group to other side
                for (int[] group : validGroups) {
                    int groupMask = group[0], maxTime = group[1];
                    if ((curr.mask & groupMask) != 0)
                        continue; // group already transferred

                    double travelTime = maxTime * mul[curr.stage];
                    int newStage = (curr.stage + (int) Math.floor(travelTime)) % m;
                    int newMask = curr.mask | groupMask;

                    if (!visited[newMask][newStage][1]) {
                        pq.offer(new State(newMask, newStage, 1, curr.cost + travelTime));
                    }
                }
            } else {
                // Boat is at destination → send 1 person back
                for (int i = 0; i < n; i++) {
                    if ((curr.mask & (1 << i)) != 0) {
                        double travelTime = time[i] * mul[curr.stage];
                        int newStage = (curr.stage + (int) Math.floor(travelTime)) % m;
                        int newMask = curr.mask ^ (1 << i); // person returns

                        if (!visited[newMask][newStage][0]) {
                            pq.offer(new State(newMask, newStage, 0, curr.cost + travelTime));
                        }
                    }
                }
            }
        }

        return -1; // If we never reached the full mask
    }
}
