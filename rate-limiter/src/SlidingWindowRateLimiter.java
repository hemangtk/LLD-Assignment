import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Sliding Window Counter algorithm.
 *
 * Combines the current fixed window count with a weighted portion of the previous window.
 * This smooths out the burst problem of pure fixed windows.
 *
 * Formula: effectiveCount = prevCount * overlapFraction + currentCount
 *
 * Trade-offs:
 * + Smoother than fixed window — no 2x burst at edges
 * + Still O(1) memory per key (just two counters)
 * - Approximate — uses weighted estimate, not exact sliding log
 */
public class SlidingWindowRateLimiter implements RateLimiter {
    private final int maxRequests;
    private final long windowSizeMillis;
    private final Map<String, SlidingCounter> counters;

    public SlidingWindowRateLimiter(int maxRequests, long windowSizeMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSizeMillis;
        this.counters = new ConcurrentHashMap<>();
    }

    @Override
    public boolean tryConsume(String key) {
        SlidingCounter counter = counters.computeIfAbsent(key, k -> new SlidingCounter());
        return counter.tryIncrement();
    }

    /** Thread-safe sliding window counter for a single key. */
    private class SlidingCounter {
        private long currentWindowStart;
        private int currentCount;
        private int previousCount;

        SlidingCounter() {
            this.currentWindowStart = windowOf(System.currentTimeMillis());
            this.currentCount = 0;
            this.previousCount = 0;
        }

        synchronized boolean tryIncrement() {
            long now = System.currentTimeMillis();
            long windowStart = windowOf(now);

            advanceWindow(windowStart);

            // Calculate overlap: how far into the current window are we?
            long elapsedInWindow = now - currentWindowStart;
            double overlapFraction = 1.0 - ((double) elapsedInWindow / windowSizeMillis);
            double effectiveCount = previousCount * overlapFraction + currentCount;

            if (effectiveCount < maxRequests) {
                currentCount++;
                return true;
            }
            return false;
        }

        private void advanceWindow(long windowStart) {
            if (windowStart == currentWindowStart) return;

            if (windowStart == currentWindowStart + windowSizeMillis) {
                // Moved exactly one window forward
                previousCount = currentCount;
            } else {
                // Moved more than one window — previous data is stale
                previousCount = 0;
            }
            currentCount = 0;
            currentWindowStart = windowStart;
        }
    }

    private long windowOf(long timeMillis) {
        return (timeMillis / windowSizeMillis) * windowSizeMillis;
    }
}
