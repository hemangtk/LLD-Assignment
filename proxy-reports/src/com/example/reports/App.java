package com.example.reports;

/**
 * CampusVault demo — uses ReportProxy for access control + lazy loading.
 */
public class App {

    public static void main(String[] args) {
        User student = new User("Jasleen", "STUDENT");
        User faculty = new User("Prof. Noor", "FACULTY");
        User admin = new User("Kshitij", "ADMIN");

        Report publicReport = new ReportProxy("R-101", "Orientation Plan", "PUBLIC");
        Report facultyReport = new ReportProxy("R-202", "Midterm Review", "FACULTY");
        Report adminReport = new ReportProxy("R-303", "Budget Audit", "ADMIN");

        ReportViewer viewer = new ReportViewer();

        System.out.println("=== CampusVault Demo ===\n");

        // Student can view public report
        viewer.open(publicReport, student);
        System.out.println();

        // Student cannot view faculty report
        viewer.open(facultyReport, student);
        System.out.println();

        // Faculty can view faculty report
        viewer.open(facultyReport, faculty);
        System.out.println();

        // Admin views admin report (first time — loads from disk)
        viewer.open(adminReport, admin);
        System.out.println();

        // Admin views same report again (cached — no disk load)
        viewer.open(adminReport, admin);
    }
}
