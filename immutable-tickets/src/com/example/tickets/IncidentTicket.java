package com.example.tickets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Immutable incident ticket — all fields are final, no setters.
 * Created only via the nested Builder. Use toBuilder() to create
 * a modified copy.
 */
public final class IncidentTicket {

    private final String id;
    private final String reporterEmail;
    private final String title;
    private final String description;
    private final String priority;
    private final List<String> tags;
    private final String assigneeEmail;
    private final boolean customerVisible;
    private final Integer slaMinutes;
    private final String source;

    // Only the Builder can construct a ticket
    private IncidentTicket(Builder b) {
        this.id = b.id;
        this.reporterEmail = b.reporterEmail;
        this.title = b.title;
        this.description = b.description;
        this.priority = b.priority;
        this.tags = Collections.unmodifiableList(new ArrayList<>(b.tags));
        this.assigneeEmail = b.assigneeEmail;
        this.customerVisible = b.customerVisible;
        this.slaMinutes = b.slaMinutes;
        this.source = b.source;
    }

    // Getters — no setters, tags returns unmodifiable copy
    public String getId() { return id; }
    public String getReporterEmail() { return reporterEmail; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public List<String> getTags() { return tags; }
    public String getAssigneeEmail() { return assigneeEmail; }
    public boolean isCustomerVisible() { return customerVisible; }
    public Integer getSlaMinutes() { return slaMinutes; }
    public String getSource() { return source; }

    /**
     * Returns a new Builder pre-filled with this ticket's values.
     * Use this to create a modified copy of the ticket.
     */
    public Builder toBuilder() {
        Builder b = new Builder(this.id, this.reporterEmail, this.title);
        b.description = this.description;
        b.priority = this.priority;
        b.tags = new ArrayList<>(this.tags);
        b.assigneeEmail = this.assigneeEmail;
        b.customerVisible = this.customerVisible;
        b.slaMinutes = this.slaMinutes;
        b.source = this.source;
        return b;
    }

    @Override
    public String toString() {
        return "IncidentTicket{" +
                "id='" + id + '\'' +
                ", reporterEmail='" + reporterEmail + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", tags=" + tags +
                ", assigneeEmail='" + assigneeEmail + '\'' +
                ", customerVisible=" + customerVisible +
                ", slaMinutes=" + slaMinutes +
                ", source='" + source + '\'' +
                '}';
    }

    /**
     * Fluent builder with centralized validation in build().
     * Required fields: id, reporterEmail, title (passed in constructor).
     * Optional fields set via fluent setters.
     */
    public static class Builder {
        // Required
        private final String id;
        private final String reporterEmail;
        private final String title;

        // Optional with defaults
        private String description;
        private String priority;
        private List<String> tags = new ArrayList<>();
        private String assigneeEmail;
        private boolean customerVisible = false;
        private Integer slaMinutes;
        private String source;

        public Builder(String id, String reporterEmail, String title) {
            this.id = id;
            this.reporterEmail = reporterEmail;
            this.title = title;
        }

        public Builder description(String val) { this.description = val; return this; }
        public Builder priority(String val) { this.priority = val; return this; }
        public Builder tags(List<String> val) { this.tags = new ArrayList<>(val); return this; }
        public Builder assigneeEmail(String val) { this.assigneeEmail = val; return this; }
        public Builder customerVisible(boolean val) { this.customerVisible = val; return this; }
        public Builder slaMinutes(Integer val) { this.slaMinutes = val; return this; }
        public Builder source(String val) { this.source = val; return this; }

        public Builder addTag(String tag) {
            this.tags.add(tag);
            return this;
        }

        /**
         * Validates all fields and returns an immutable IncidentTicket.
         * This is the ONLY place where validation happens.
         */
        public IncidentTicket build() {
            Validation.requireTicketId(id);
            Validation.requireEmail(reporterEmail, "reporterEmail");
            Validation.requireNonBlank(title, "title");
            Validation.requireMaxLen(title, 80, "title");
            Validation.requireOneOf(priority, "priority", "LOW", "MEDIUM", "HIGH", "CRITICAL");
            Validation.requireRange(slaMinutes, 5, 7200, "slaMinutes");

            if (assigneeEmail != null && !assigneeEmail.trim().isEmpty()) {
                Validation.requireEmail(assigneeEmail, "assigneeEmail");
            }

            return new IncidentTicket(this);
        }
    }
}
