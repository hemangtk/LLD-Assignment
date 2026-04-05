public class InternalService {
    private final RateLimiter rateLimiter;
    private final ExternalService externalService;

    public InternalService(RateLimiter rateLimiter, ExternalService externalService) {
        this.rateLimiter = rateLimiter;
        this.externalService = externalService;
    }

    public String handleRequest(String clientKey, String request, boolean needsExternalCall) {
        System.out.println("  [Service] Processing: " + request);

        if (!needsExternalCall) {
            System.out.println("  [Service] No external call needed — responding from cache/local");
            return "LocalResponse for: " + request;
        }

        if (!rateLimiter.tryConsume(clientKey)) {
            System.out.println("  [Service] RATE LIMITED — external call denied for key: " + clientKey);
            return "Error: Rate limit exceeded. Try again later.";
        }

        String response = externalService.call(request);
        System.out.println("  [Service] External call SUCCESS: " + response);
        return response;
    }
}
