import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Fixed Window Counter algorithm.
 *
 * Divides time into fixed windows (e.g., every minute).
 * Each key gets a counter per window. Resets when the window changes.
 *
 * Trade-offs:
 * + Simple, low memory (one counter per key)
 * - Burst at window edges: a user can make 2x the limit across two adjacent windows
 */
public class FixedWindowRateLimiter implements RateLimiter {
    private final int maxRequests;
    private final long windowSizeMillis;
    private final Map<String, WindowCounter> counters;

    public FixedWindowRateLimiter(int maxRequests, long windowSizeMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSizeMillis;
        this.counters = new ConcurrentHashMap<>();
    }

    @Override
    public boolean tryConsume(String key) {
        WindowCounter counter = counters.computeIfAbsent(key, k -> new WindowCounter());
        return counter.tryIncrement();
    }

    /** Thread-safe counter for a single key's fixed window. */
    private class WindowCounter {
        private long windowStart;
        private int count;

        WindowCounter() {
            this.windowStart = currentWindow();
            this.count = 0;
        }

        synchronized boolean tryIncrement() {
            long now = currentWindow();
            if (now != windowStart) {
                // New window — reset
                windowStart = now;
                count = 0;
            }
            if (count < maxRequests) {
                count++;
                return true;
            }
            return false;
        }
    }

    /** Returns the start of the current window. */
    private long currentWindow() {
        return System.currentTimeMillis() / windowSizeMillis;
    }
}
