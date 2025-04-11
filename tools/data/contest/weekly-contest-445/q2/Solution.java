import java.util.*;

class Router {

    private static class Packet {
        int source, destination, timestamp;
        Packet(int source, int destination, int timestamp) {
            this.source = source;
            this.destination = destination;
            this.timestamp = timestamp;
        }
    }

    private int memoryLimit;
    private Queue<Packet> queue;
    private Set<String> uniquePackets;
    private Map<Integer, TreeMap<Integer, Integer>> destToTimestamps;

    public Router(int memoryLimit) {
        this.memoryLimit = memoryLimit;
        this.queue = new LinkedList<>();
        this.uniquePackets = new HashSet<>();
        this.destToTimestamps = new HashMap<>();
    }

    public boolean addPacket(int source, int destination, int timestamp) {
        String key = source + "#" + destination + "#" + timestamp;

        if (uniquePackets.contains(key)) {
            return false; // duplicate
        }

        // Remove oldest if exceeding memory limit
        if (queue.size() == memoryLimit) {
            Packet old = queue.poll();
            String oldKey = old.source + "#" + old.destination + "#" + old.timestamp;
            uniquePackets.remove(oldKey);

            // Update timestamp map
            TreeMap<Integer, Integer> timeMap = destToTimestamps.get(old.destination);
            timeMap.put(old.timestamp, timeMap.get(old.timestamp) - 1);
            if (timeMap.get(old.timestamp) == 0) {
                timeMap.remove(old.timestamp);
            }
        }

        // Add new packet
        Packet packet = new Packet(source, destination, timestamp);
        queue.offer(packet);
        uniquePackets.add(key);

        destToTimestamps.putIfAbsent(destination, new TreeMap<>());
        TreeMap<Integer, Integer> timeMap = destToTimestamps.get(destination);
        timeMap.put(timestamp, timeMap.getOrDefault(timestamp, 0) + 1);

        return true;
    }

    public int[] forwardPacket() {
        if (queue.isEmpty()) return new int[0];

        Packet packet = queue.poll();
        String key = packet.source + "#" + packet.destination + "#" + packet.timestamp;
        uniquePackets.remove(key);

        TreeMap<Integer, Integer> timeMap = destToTimestamps.get(packet.destination);
        timeMap.put(packet.timestamp, timeMap.get(packet.timestamp) - 1);
        if (timeMap.get(packet.timestamp) == 0) {
            timeMap.remove(packet.timestamp);
        }

        return new int[]{packet.source, packet.destination, packet.timestamp};
    }

    public int getCount(int destination, int startTime, int endTime) {
        if (!destToTimestamps.containsKey(destination)) return 0;

        TreeMap<Integer, Integer> timeMap = destToTimestamps.get(destination);
        int count = 0;

        for (int value : timeMap.subMap(startTime, true, endTime, true).values()) {
            count += value;
        }

        return count;
    }
}
