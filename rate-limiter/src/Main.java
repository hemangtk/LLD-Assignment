public class Main {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("========================================");
        System.out.println("  FIXED WINDOW RATE LIMITER DEMO");
        System.out.println("========================================");

        RateLimiterConfig config = RateLimiterConfig.perMinute(5);
        RateLimiter fixedLimiter = RateLimiterFactory.create(
                RateLimiterFactory.Algorithm.FIXED_WINDOW, config);

        InternalService service1 = new InternalService(fixedLimiter, new ExternalService());

        System.out.println("\n--- Requests without external call (no rate limiting) ---");
        service1.handleRequest("T1", "get-cached-data", false);
        service1.handleRequest("T1", "get-local-config", false);

        System.out.println("\n--- Requests with external call (rate limited, limit=5/min) ---");
        for (int i = 1; i <= 7; i++) {
            String result = service1.handleRequest("T1", "external-query-" + i, true);
            System.out.println("  -> Result: " + result + "\n");
        }

        System.out.println("--- Different tenant T2 (separate quota) ---");
        service1.handleRequest("T2", "T2-external-query", true);

        System.out.println("\n\n========================================");
        System.out.println("  SLIDING WINDOW RATE LIMITER DEMO");
        System.out.println("========================================");

        RateLimiter slidingLimiter = RateLimiterFactory.create(
                RateLimiterFactory.Algorithm.SLIDING_WINDOW, config);

        InternalService service2 = new InternalService(slidingLimiter, new ExternalService());

        System.out.println("\n--- Sliding window: 5 calls per minute for T1 ---");
        for (int i = 1; i <= 7; i++) {
            String result = service2.handleRequest("T1", "sliding-query-" + i, true);
            System.out.println("  -> Result: " + result + "\n");
        }

        System.out.println("\n========================================");
        System.out.println("  CONCURRENCY TEST");
        System.out.println("========================================");

        RateLimiterConfig strictConfig = RateLimiterConfig.perMinute(3);
        RateLimiter concurrentLimiter = RateLimiterFactory.create(
                RateLimiterFactory.Algorithm.FIXED_WINDOW, strictConfig);
        InternalService service3 = new InternalService(concurrentLimiter, new ExternalService());

        System.out.println("\n--- 6 threads racing for 3 slots (tenant T1) ---");
        Thread[] threads = new Thread[6];
        for (int i = 0; i < 6; i++) {
            int idx = i;
            threads[i] = new Thread(() -> {
                String result = service3.handleRequest("T1", "concurrent-" + idx, true);
                System.out.println("  Thread-" + idx + " -> " + result);
            });
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.println("\n--- Done ---");
    }
}
