/**
 * Demo: Distributed Cache with LRU eviction and modulo-based distribution.
 */
public class Main {
    public static void main(String[] args) {
        // --- Setup database with seed data ---
        InMemoryDatabase db = new InMemoryDatabase();
        db.seed("user:1", "Alice");
        db.seed("user:2", "Bob");
        db.seed("user:3", "Charlie");
        db.seed("user:4", "Diana");
        db.seed("user:5", "Eve");
        db.seed("user:6", "Frank");
        db.seed("user:7", "Grace");
        db.seed("user:8", "Heidi");

        // --- Create cache: 3 nodes, capacity 2 each, modulo distribution ---
        DistributedCache cache = new DistributedCache(
                3, 2,
                new ModuloDistributionStrategy(),
                db
        );

        // === Demo 1: Cache miss → fetches from DB ===
        System.out.println("=== Cache Miss (fetches from DB) ===");
        System.out.println("  Result: " + cache.get("user:1"));
        System.out.println("  Result: " + cache.get("user:2"));
        System.out.println("  Result: " + cache.get("user:3"));

        // === Demo 2: Cache hit ===
        System.out.println("\n=== Cache Hit ===");
        System.out.println("  Result: " + cache.get("user:1"));

        // === Demo 3: put() — write through to cache + DB ===
        System.out.println("\n=== Put (write-through) ===");
        cache.put("order:100", "Laptop");
        cache.put("order:200", "Phone");
        System.out.println("  get order:100 -> " + cache.get("order:100"));

        // === Demo 4: Eviction — fill a node beyond capacity ===
        System.out.println("\n=== Eviction Demo ===");
        cache.printStatus();
        // Adding more keys will trigger eviction on nodes that are full
        cache.put("item:A", "Keyboard");
        cache.put("item:B", "Mouse");
        cache.put("item:C", "Monitor");
        cache.put("item:D", "Headset");
        cache.printStatus();

        // === Demo 5: Key not in DB ===
        System.out.println("\n=== Key Not Found ===");
        String missing = cache.get("user:999");
        System.out.println("  Result: " + missing);

        // === Demo 6: Distribution — show which node each key goes to ===
        System.out.println("\n=== Key Distribution ===");
        ModuloDistributionStrategy strategy = new ModuloDistributionStrategy();
        String[] keys = {"user:1", "user:2", "user:3", "order:100", "order:200", "item:A"};
        for (String key : keys) {
            System.out.println("  " + key + " -> Node-" + strategy.getNodeIndex(key, 3));
        }
    }
}
