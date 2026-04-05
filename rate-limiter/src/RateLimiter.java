/**
 * Pluggable rate limiting interface.
 * Internal services call tryConsume() before making an external resource call.
 */
public interface RateLimiter {
    /**
     * Checks if the request identified by the given key is allowed.
     * @param key rate limiting key (e.g., customerId, tenantId, apiKey)
     * @return true if the call is allowed, false if rate limit exceeded
     */
    boolean tryConsume(String key);
}
