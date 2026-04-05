import java.util.ArrayList;
import java.util.List;

/**
 * Distributed cache that routes keys across multiple CacheNodes.
 * - Pluggable distribution strategy (modulo, consistent hashing, etc.)
 * - Pluggable eviction policy per node (LRU, LFU, MRU, etc.)
 * - On cache miss: fetches from database, stores in cache, then returns
 */
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

    /**
     * get(key): Returns value from cache if present.
     * On cache miss, fetches from DB, caches it, then returns.
     */
    public String get(String key) {
        int index = distributionStrategy.getNodeIndex(key, nodes.size());
        CacheNode node = nodes.get(index);

        String value = node.get(key);
        if (value != null) {
            System.out.println("  Cache HIT  [" + node.getNodeId() + "] key=" + key);
            return value;
        }

        // Cache miss — fetch from database
        System.out.println("  Cache MISS [" + node.getNodeId() + "] key=" + key + " -> fetching from DB");
        value = database.fetch(key);
        if (value != null) {
            node.put(key, value);
        }
        return value;
    }

    /**
     * put(key, value): Stores in the appropriate cache node + updates DB.
     */
    public void put(String key, String value) {
        int index = distributionStrategy.getNodeIndex(key, nodes.size());
        CacheNode node = nodes.get(index);
        node.put(key, value);
        database.store(key, value);
        System.out.println("  PUT [" + node.getNodeId() + "] key=" + key + " value=" + value);
    }

    /** Print status of all nodes. */
    public void printStatus() {
        System.out.println("--- Cache Status ---");
        for (CacheNode node : nodes) {
            System.out.println("  " + node);
        }
    }

    public List<CacheNode> getNodes() { return nodes; }
}
