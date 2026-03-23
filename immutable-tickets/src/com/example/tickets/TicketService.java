package com.example.tickets;

import java.util.Arrays;

/**
 * Service layer for creating and updating tickets.
 *
 * All "updates" return a NEW IncidentTicket — the original is never mutated.
 */
public class TicketService {

    public IncidentTicket createTicket(String id, String reporterEmail, String title) {
        return new IncidentTicket.Builder(id, reporterEmail, title)
                .priority("MEDIUM")
                .source("CLI")
                .customerVisible(false)
                .addTag("NEW")
                .build();
    }

    public IncidentTicket escalateToCritical(IncidentTicket ticket) {
        return ticket.toBuilder()
                .priority("CRITICAL")
                .addTag("ESCALATED")
                .build();
    }

    public IncidentTicket assign(IncidentTicket ticket, String assigneeEmail) {
        return ticket.toBuilder()
                .assigneeEmail(assigneeEmail)
                .build();
    }
}
