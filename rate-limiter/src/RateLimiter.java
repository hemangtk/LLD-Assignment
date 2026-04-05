public interface RateLimiter {
    boolean tryConsume(String key);
}
