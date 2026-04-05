/**
 * Pluggable distribution strategy interface.
 * Decides which cache node stores a given key.
 */
public interface DistributionStrategy {
    /** Returns the node index (0-based) for the given key. */
    int getNodeIndex(String key, int totalNodes);
}
