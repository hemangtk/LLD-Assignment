/**
 * Pluggable eviction policy interface.
 * Implementations decide which entry to evict when a cache node is full.
 */
public interface EvictionPolicy<K, V> {
    /** Called when a key is accessed (get or put). */
    void onAccess(K key);

    /** Called when a new entry is added. */
    void onInsert(K key, V value);

    /** Called when an entry is removed (eviction or explicit). */
    void onRemove(K key);

    /** Returns the key that should be evicted next. */
    K evict();
}
