package com.example.reports;

/**
 * Real subject — performs the expensive file loading in the constructor.
 * Content is loaded once and stored for display.
 */
public class RealReport implements Report {

    private final String reportId;
    private final String title;
    private final String classification;
    private final String body;

    public RealReport(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
        this.body = fetchContent();
    }

    private String fetchContent() {
        System.out.println("  [loading] reading report " + reportId + " from disk...");
        try {
            Thread.sleep(120);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Internal report body for " + title;
    }

    @Override
    public void display(User user) {
        System.out.println("REPORT -> id=" + reportId
                + " title=" + title
                + " classification=" + classification
                + " openedBy=" + user.getName());
        System.out.println("CONTENT: " + body);
    }

    public String getClassification() {
        return classification;
    }
}
