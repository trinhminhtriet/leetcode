class Router {
    private int limit;
    // works also as a deque and the golden sorce of existing packets
    private LinkedHashSet<Packet> set;
    // the values which are lists is append-only, so that we can do binary search
    private Map<Integer, List<Packet>> destToPacket;

    private Map<Integer, Integer> destToStartIdx;

    public Router(int memoryLimit) {
        limit = memoryLimit;
        set = new LinkedHashSet<>();
        destToPacket = new HashMap<>();
        destToStartIdx = new HashMap<>();
    }

    public boolean addPacket(int source, int destination, int timestamp) {
        Packet p = new Packet(source, destination, timestamp);
        if (set.contains(p))
            return false;
        if (set.size() == limit) {
            this.forwardPacket();
        }
        set.add(p);
        destToPacket.computeIfAbsent(p.dest, k -> new ArrayList<>()).add(p);
        destToStartIdx.putIfAbsent(p.dest, 0);
        return true;
    }

    public int[] forwardPacket() {
        if (set.size() == 0)
            return new int[0];

        Packet p = set.iterator().next();
        set.remove(p);
        destToStartIdx.merge(p.dest, 1, Integer::sum);
        return new int[] { p.src, p.dest, p.t };
    }

    public int getCount(int dest, int s, int e) {
        List<Packet> list = destToPacket.get(dest);
        int lo = destToStartIdx.get(dest);
        int left = floor(list, lo, s);
        int right = upper(list, lo, e);
        return right - left;
    }

    // find the smallest idx such that its val >= target
    private int floor(List<Packet> list, int lo, int target) {
        int hi = list.size();
        while (lo < hi) {
            int mid = lo + (hi - lo >> 1);
            if (list.get(mid).t >= target) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    // find the smallest idx such that its val > target
    private int upper(List<Packet> list, int lo, int target) {
        int hi = list.size();
        while (lo < hi) {
            int mid = lo + (hi - lo >> 1);
            if (list.get(mid).t > target) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    public static class Packet {
        int src;
        int dest;
        int t;

        public Packet(int src, int dest, int timestamp) {
            this.src = src;
            this.dest = dest;
            this.t = timestamp;
        }

        @Override
        public int hashCode() {
            return Objects.hash(src, dest, t);
        }

        @Override
        public boolean equals(Object pp) {
            Packet p = (Packet) pp;
            return src == p.src && dest == p.dest && t == p.t;
        }
    }
}

/**
 * Your Router object will be instantiated and called as such:
 * Router obj = new Router(memoryLimit);
 * boolean param_1 = obj.addPacket(source,destination,timestamp);
 * int[] param_2 = obj.forwardPacket();
 * int param_3 = obj.getCount(destination,startTime,endTime);
 */
