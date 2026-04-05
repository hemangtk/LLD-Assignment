import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private long currentWindow() {
        return System.currentTimeMillis() / windowSizeMillis;
    }
}
