import java.util.ArrayList;
import java.util.List;

public class DistributedCache {
    private final List<CacheNode> nodes;
    private final DistributionStrategy distributionStrategy;
    private final Database database;

    public DistributedCache(int nodeCount, int capacityPerNode,
                            DistributionStrategy distributionStrategy,
                            Database database) {
        this.distributionStrategy = distributionStrategy;
        this.database = database;
        this.nodes = new ArrayList<>();

        for (int i = 0; i < nodeCount; i++) {
            EvictionPolicy<String, String> policy = new LRUEvictionPolicy<>();
            nodes.add(new CacheNode("Node-" + i, capacityPerNode, policy));
        }
    }

    public String get(String key) {
        int index = distributionStrategy.getNodeIndex(key, nodes.size());
        CacheNode node = nodes.get(index);

        String value = node.get(key);
        if (value != null) {
            System.out.println("  Cache HIT  [" + node.getNodeId() + "] key=" + key);
            return value;
        }

        System.out.println("  Cache MISS [" + node.getNodeId() + "] key=" + key + " -> fetching from DB");
        value = database.fetch(key);
        if (value != null) {
            node.put(key, value);
        }
        return value;
    }

    public void put(String key, String value) {
        int index = distributionStrategy.getNodeIndex(key, nodes.size());
        CacheNode node = nodes.get(index);
        node.put(key, value);
        database.store(key, value);
        System.out.println("  PUT [" + node.getNodeId() + "] key=" + key + " value=" + value);
    }

    public void printStatus() {
        System.out.println("--- Cache Status ---");
        for (CacheNode node : nodes) {
            System.out.println("  " + node);
        }
    }

    public List<CacheNode> getNodes() { return nodes; }
}
