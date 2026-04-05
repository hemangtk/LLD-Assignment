/**
 * Internal service that processes client requests.
 * Uses rate limiter ONLY when an external call is needed.
 */
public class InternalService {
    private final RateLimiter rateLimiter;
    private final ExternalService externalService;

    public InternalService(RateLimiter rateLimiter, ExternalService externalService) {
        this.rateLimiter = rateLimiter;
        this.externalService = externalService;
    }

    /**
     * Handles a client request.
     * @param clientKey rate limiting key (customerId, tenantId, etc.)
     * @param request the request payload
     * @param needsExternalCall whether business logic requires the external resource
     */
    public String handleRequest(String clientKey, String request, boolean needsExternalCall) {
        // Step 1: Run business logic
        System.out.println("  [Service] Processing: " + request);

        // Step 2: If no external call needed, return directly
        if (!needsExternalCall) {
            System.out.println("  [Service] No external call needed — responding from cache/local");
            return "LocalResponse for: " + request;
        }

        // Step 3: Check rate limiter before calling external resource
        if (!rateLimiter.tryConsume(clientKey)) {
            System.out.println("  [Service] RATE LIMITED — external call denied for key: " + clientKey);
            return "Error: Rate limit exceeded. Try again later.";
        }

        // Step 4: Call external resource
        String response = externalService.call(request);
        System.out.println("  [Service] External call SUCCESS: " + response);
        return response;
    }
}
