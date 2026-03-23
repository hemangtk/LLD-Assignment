package com.example.metrics;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Thread-safe, lazy-initialized Singleton using the static holder idiom.
 *
 * Fixes:
 *  1) Private constructor + static holder for lazy thread-safe init
 *  2) Static flag blocks reflection-based second construction
 *  3) readResolve() returns singleton on deserialization
 */
public class MetricsRegistry implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<String, Long> counters = new HashMap<>();

    private static boolean alreadyCreated = false;

    // Private constructor — blocks direct instantiation and reflection
    private MetricsRegistry() {
        synchronized (MetricsRegistry.class) {
            if (alreadyCreated) {
                throw new IllegalStateException(
                        "Cannot create another instance. Use getInstance().");
            }
            alreadyCreated = true;
        }
    }

    // Static holder — JVM guarantees thread-safe lazy loading
    private static class RegistryHolder {
        private static final MetricsRegistry SINGLE_INSTANCE = new MetricsRegistry();
    }

    public static MetricsRegistry getInstance() {
        return RegistryHolder.SINGLE_INSTANCE;
    }

    // Returns the singleton when deserialized instead of creating a new object
    @Serial
    private Object readResolve() {
        return getInstance();
    }

    public synchronized void setCount(String key, long value) {
        counters.put(key, value);
    }

    public synchronized void increment(String key) {
        counters.put(key, getCount(key) + 1);
    }

    public synchronized long getCount(String key) {
        return counters.getOrDefault(key, 0L);
    }

    public synchronized Map<String, Long> getAll() {
        return Collections.unmodifiableMap(new HashMap<>(counters));
    }
}
