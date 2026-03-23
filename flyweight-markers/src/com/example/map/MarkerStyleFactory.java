package com.example.map;

import java.util.HashMap;
import java.util.Map;

/**
 * Flyweight factory — caches MarkerStyle instances by a composite key.
 * Returns the same object for identical style configurations.
 */
public class MarkerStyleFactory {

    private final Map<String, MarkerStyle> styleCache = new HashMap<>();

    public MarkerStyle get(String shape, String color, int size, boolean filled) {
        String key = String.join("|", shape, color, String.valueOf(size), filled ? "F" : "O");
        return styleCache.computeIfAbsent(key, k -> new MarkerStyle(shape, color, size, filled));
    }

    public int cacheSize() {
        return styleCache.size();
    }
}
