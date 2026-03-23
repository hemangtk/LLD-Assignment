import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParkingLot {
    private final List<Slot> slots;
    private final Map<String, Gate> gates;
    private final Set<String> activeTickets;
    private final SlotAssignmentStrategy assignmentStrategy;
    private final PricingStrategy pricingStrategy;

    public ParkingLot(SlotAssignmentStrategy assignmentStrategy, PricingStrategy pricingStrategy) {
        this.slots = new ArrayList<>();
        this.gates = new LinkedHashMap<>();
        this.activeTickets = new HashSet<>();
        this.assignmentStrategy = assignmentStrategy;
        this.pricingStrategy = pricingStrategy;
    }

    public void addSlot(Slot slot) {
        slots.add(slot);
    }

    public void addGate(Gate gate) {
        gates.put(gate.getId(), gate);
    }

    /**
     * Parks a vehicle and returns the generated ticket.
     */
    public Ticket park(Vehicle vehicle, long entryTime, String entryGateId) {
        Gate gate = gates.get(entryGateId);
        if (gate == null) {
            System.out.println("Error: Gate " + entryGateId + " not found.");
            return null;
        }

        System.out.println("Attempting to park " + vehicle + " at Gate " + gate.getId());

        Slot assigned = assignmentStrategy.findSlot(gate, slots);
        if (assigned == null) {
            System.out.println("Error: Parking lot is full.");
            return null;
        }

        assigned.occupy();
        Ticket ticket = new Ticket(vehicle, assigned, entryTime);
        activeTickets.add(ticket.getTicketId());
        System.out.println("Assigned: " + assigned + " | Ticket: " + ticket.getTicketId());
        return ticket;
    }

    /**
     * Returns current availability of parking slots.
     */
    public void status() {
        int total = slots.size();
        int available = 0;
        for (Slot slot : slots) {
            if (!slot.isOccupied()) available++;
        }

        System.out.println("\n--- Parking Lot Status ---");
        System.out.println("Available: " + available + " / " + total + " slots");
        System.out.println("--------------------------\n");
    }

    /**
     * Processes vehicle exit and returns the bill amount.
     */
    public double exit(Ticket ticket, long exitTime) {
        if (!activeTickets.remove(ticket.getTicketId())) {
            System.out.println("Error: Invalid or already used ticket " + ticket.getTicketId());
            return -1;
        }

        ticket.getSlot().free();
        double fee = pricingStrategy.calculateFee(ticket.getEntryTime(), exitTime);

        System.out.println("Vehicle " + ticket.getVehicle().getLicensePlate()
                + " exited from " + ticket.getSlot()
                + " | Duration fee: Rs." + String.format("%.2f", fee));
        return fee;
    }
}
