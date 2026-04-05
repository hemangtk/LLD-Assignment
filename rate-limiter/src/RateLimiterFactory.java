/**
 * Factory to create rate limiters by algorithm name.
 * Callers can switch algorithms without changing business logic.
 */
public class RateLimiterFactory {

    public enum Algorithm {
        FIXED_WINDOW,
        SLIDING_WINDOW
    }

    public static RateLimiter create(Algorithm algorithm, RateLimiterConfig config) {
        switch (algorithm) {
            case FIXED_WINDOW:
                return new FixedWindowRateLimiter(config.getMaxRequests(), config.getWindowSizeMillis());
            case SLIDING_WINDOW:
                return new SlidingWindowRateLimiter(config.getMaxRequests(), config.getWindowSizeMillis());
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }
    }
}
