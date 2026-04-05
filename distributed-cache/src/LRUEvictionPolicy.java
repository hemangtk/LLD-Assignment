import java.util.HashMap;
import java.util.Map;

/**
 * Least Recently Used eviction policy.
 * Uses a doubly-linked list + HashMap for O(1) access and eviction.
 */
public class LRUEvictionPolicy<K, V> implements EvictionPolicy<K, V> {

    private static class Node<K> {
        K key;
        Node<K> prev, next;
        Node(K key) { this.key = key; }
    }

    private final Node<K> head; // most recent
    private final Node<K> tail; // least recent
    private final Map<K, Node<K>> nodeMap;

    public LRUEvictionPolicy() {
        head = new Node<>(null);
        tail = new Node<>(null);
        head.next = tail;
        tail.prev = head;
        nodeMap = new HashMap<>();
    }

    @Override
    public void onAccess(K key) {
        Node<K> node = nodeMap.get(key);
        if (node != null) {
            detach(node);
            attachToFront(node);
        }
    }

    @Override
    public void onInsert(K key, V value) {
        Node<K> node = new Node<>(key);
        nodeMap.put(key, node);
        attachToFront(node);
    }

    @Override
    public void onRemove(K key) {
        Node<K> node = nodeMap.remove(key);
        if (node != null) {
            detach(node);
        }
    }

    @Override
    public K evict() {
        if (tail.prev == head) return null; // empty
        Node<K> lru = tail.prev;
        detach(lru);
        nodeMap.remove(lru.key);
        return lru.key;
    }

    private void detach(Node<K> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void attachToFront(Node<K> node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
}
