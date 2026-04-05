/**
 * Configuration for rate limiting — max requests and window size.
 */
public class RateLimiterConfig {
    private final int maxRequests;
    private final long windowSizeMillis;

    public RateLimiterConfig(int maxRequests, long windowSizeMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSizeMillis;
    }

    public int getMaxRequests() { return maxRequests; }
    public long getWindowSizeMillis() { return windowSizeMillis; }

    /** 100 requests per minute. */
    public static RateLimiterConfig perMinute(int max) {
        return new RateLimiterConfig(max, 60_000);
    }

    /** 1000 requests per hour. */
    public static RateLimiterConfig perHour(int max) {
        return new RateLimiterConfig(max, 3_600_000);
    }

    @Override
    public String toString() {
        return maxRequests + " requests per " + windowSizeMillis + "ms";
    }
}
