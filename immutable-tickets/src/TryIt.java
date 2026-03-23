import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;

import java.util.List;

/**
 * Demonstrates immutability:
 * - Building a ticket
 * - "Updating" by creating a new instance (original stays unchanged)
 * - Tags list is not mutable from outside
 */
public class TryIt {

    public static void main(String[] args) {
        TicketService service = new TicketService();

        // 1. Create a ticket via the service
        IncidentTicket original = service.createTicket(
                "TCK-1001",
                "reporter@example.com",
                "Payment failing on checkout"
        );
        System.out.println("Created : " + original);

        // 2. Assign and escalate — each returns a NEW ticket
        IncidentTicket assigned = service.assign(original, "agent@example.com");
        IncidentTicket escalated = service.escalateToCritical(assigned);

        System.out.println("\nOriginal : " + original);
        System.out.println("Assigned : " + assigned);
        System.out.println("Escalated: " + escalated);

        // 3. Try to mutate the tags list from outside — should fail
        List<String> tags = escalated.getTags();
        try {
            tags.add("HACKED_FROM_OUTSIDE");
            System.out.println("\nBUG: Mutation succeeded!");
        } catch (UnsupportedOperationException e) {
            System.out.println("\nExternal tag mutation blocked!");
        }

        // 4. Original is truly unchanged
        System.out.println("\nOriginal after all updates: " + original);

        // 5. Validation demo — invalid ticket should throw
        try {
            new IncidentTicket.Builder("", "bad-email", "")
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("\nValidation caught: " + e.getMessage());
        }
    }
}
