package com.example.reports;

/**
 * Proxy that wraps the real report with:
 *  1. Access control — blocks unauthorized users before loading
 *  2. Lazy loading — RealReport is only created on first authorized access
 *  3. Caching — subsequent displays reuse the already-loaded report
 */
public class ReportProxy implements Report {

    private final String reportId;
    private final String title;
    private final String classification;
    private final AccessControl accessControl = new AccessControl();

    private RealReport loaded;

    public ReportProxy(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
    }

    @Override
    public void display(User user) {
        if (!accessControl.canAccess(user, classification)) {
            System.out.println("ACCESS DENIED: " + user.getName()
                    + " [" + user.getRole() + "] cannot view "
                    + classification + " report " + reportId);
            return;
        }

        if (loaded == null) {
            System.out.println("[proxy] lazy-loading report " + reportId + " for first time...");
            loaded = new RealReport(reportId, title, classification);
        } else {
            System.out.println("[proxy] serving cached report " + reportId);
        }

        loaded.display(user);
    }
}
