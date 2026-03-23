package com.example.reports;

/**
 * Viewer depends on the Report interface, not any concrete class.
 */
public class ReportViewer {

    public void open(Report report, User user) {
        report.display(user);
    }
}
