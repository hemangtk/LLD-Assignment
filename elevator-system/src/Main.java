public class Main {
    public static void main(String[] args) {
        // 10 floors (0-9), 3 lifts, default 700 kg weight limit
        Building building = new Building(10, 3, 700,
                new NearestLiftStrategy(),
                new SweepSchedulingStrategy());

        // Different weight limits per cart
        building.setLiftWeightLimit(0, 700);
        building.setLiftWeightLimit(1, 800);
        building.setLiftWeightLimit(2, 600);

        System.out.println("=== Elevator System Demo ===\n");

        // --- 1. External floor calls (up/down buttons) ---
        System.out.println("-- External requests --");
        building.callFromFloor(3, Direction.UP);
        building.callFromFloor(7, Direction.DOWN);

        // --- 2. Internal cabin request (floor button inside lift) ---
        System.out.println("\n-- Cabin request: Lift 0 -> floor 8 --");
        building.selectFloorInLift(0, 8);

        // --- Simulate ---
        System.out.println("\n-- Simulation --");
        for (int t = 1; t <= 10; t++) {
            System.out.println("\nTick " + t + ":");
            building.tick();
            building.printStatus();
        }

        // --- 3. Open/Close door buttons inside ---
        System.out.println("\n-- Open/Close door buttons --");
        building.pressOpenDoor(0);
        building.pressCloseDoor(0);

        // --- 4. Emergency button ---
        System.out.println("\n-- Emergency button on Lift 2 --");
        building.pressEmergency(2);
        building.printStatus();

        // --- 5. Lift maintenance ---
        System.out.println("\n-- Lift 1 under maintenance --");
        building.setLiftMaintenance(1, true);
        building.callFromFloor(5, Direction.UP); // won't go to lift 1

        // --- 6. Floor maintenance ---
        System.out.println("\n-- Floor 4 under maintenance --");
        building.setFloorMaintenance(4, true);
        building.callFromFloor(4, Direction.UP); // blocked

        // --- 7. Overweight ---
        System.out.println("\n-- Overweight: Lift 0 loaded to 750 kg (limit 700) --");
        building.setLiftWeight(0, 750);
        building.selectFloorInLift(0, 2);
        building.tick();
        building.setLiftWeight(0, 500);
        System.out.println("Weight reduced — lift can move again.");

        // --- 8. Task scheduling demo ---
        System.out.println("\n-- Task scheduling: Lift 1 stops at 9, 6, then 2, 0 --");
        building.setLiftMaintenance(1, false);
        building.selectFloorInLift(1, 9);
        building.selectFloorInLift(1, 6);
        for (int t = 1; t <= 10; t++) building.tick();
        building.selectFloorInLift(1, 2);
        building.selectFloorInLift(1, 0);
        for (int t = 1; t <= 10; t++) building.tick();

        System.out.println("\nFinal status:");
        building.printStatus();
    }
}
