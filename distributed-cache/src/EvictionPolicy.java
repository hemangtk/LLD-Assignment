public interface EvictionPolicy<K, V> {
    void onAccess(K key);
    void onInsert(K key, V value);
    void onRemove(K key);
    K evict();
}
