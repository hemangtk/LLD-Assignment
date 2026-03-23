import java.util.UUID;

public class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final Slot slot;
    private final long entryTime;

    public Ticket(Vehicle vehicle, Slot slot, long entryTime) {
        this.ticketId = UUID.randomUUID().toString().substring(0, 8);
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = entryTime;
    }

    public String getTicketId() { return ticketId; }
    public Vehicle getVehicle() { return vehicle; }
    public Slot getSlot() { return slot; }
    public long getEntryTime() { return entryTime; }

    @Override
    public String toString() {
        return "Ticket[" + ticketId + "] " + vehicle + " -> Slot " + slot;
    }
}
