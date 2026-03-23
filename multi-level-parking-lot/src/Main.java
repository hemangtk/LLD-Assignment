public class Main {
    public static void main(String[] args) {
        // --- Setup: Rs.10/hour flat rate ---
        PricingStrategy pricing = new HourlyPricingStrategy(10.0);
        SlotAssignmentStrategy assignment = new NearestSlotStrategy();

        ParkingLot lot = new ParkingLot(assignment, pricing);

        // --- Add gates ---
        lot.addGate(new Gate("G1", 1, 0, 0));
        lot.addGate(new Gate("G2", 2, 50, 50));

        // --- Add slots across floors ---
        lot.addSlot(new Slot("P1", 1, 10, 10));
        lot.addSlot(new Slot("P2", 1, 30, 30));
        lot.addSlot(new Slot("P3", 1, 20, 20));
        lot.addSlot(new Slot("P4", 2, 40, 40));
        lot.addSlot(new Slot("P5", 2, 60, 60));

        // --- Initial status ---
        lot.status();

        long now = System.currentTimeMillis();

        // --- Park vehicles ---
        Vehicle car = new Vehicle("KA-01-1234", VehicleType.CAR);
        Ticket carTicket = lot.park(car, now - 3 * 3600 * 1000, "G1");

        Vehicle bike = new Vehicle("KA-02-5678", VehicleType.TWO_WHEELER);
        Ticket bikeTicket = lot.park(bike, now - 1 * 3600 * 1000, "G1");

        Vehicle bus = new Vehicle("KA-03-9999", VehicleType.BUS);
        Ticket busTicket = lot.park(bus, now - 2 * 3600 * 1000, "G2");

        lot.status();

        // --- Exit ---
        System.out.println("=== Exits ===");
        if (carTicket != null) lot.exit(carTicket, now);
        if (bikeTicket != null) lot.exit(bikeTicket, now);
        if (busTicket != null) lot.exit(busTicket, now);

        lot.status();

        // === Edge Cases ===
        System.out.println("=== Edge Case Tests ===\n");

        // 1. Double exit
        System.out.println("-- Double exit --");
        if (carTicket != null) lot.exit(carTicket, now);

        // 2. Lot full
        System.out.println("\n-- Lot full --");
        lot.park(new Vehicle("V1", VehicleType.CAR), now, "G1");
        lot.park(new Vehicle("V2", VehicleType.CAR), now, "G1");
        lot.park(new Vehicle("V3", VehicleType.CAR), now, "G1");
        lot.park(new Vehicle("V4", VehicleType.CAR), now, "G1");
        lot.park(new Vehicle("V5", VehicleType.CAR), now, "G1");
        lot.park(new Vehicle("V6", VehicleType.CAR), now, "G1"); // should fail

        // 3. Invalid gate
        System.out.println("\n-- Invalid gate --");
        lot.park(new Vehicle("X1", VehicleType.CAR), now, "G99");

        lot.status();
    }
}
