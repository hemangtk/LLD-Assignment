import java.util.ArrayList;
import java.util.List;

/**
 * Top-level class that ties floors, lifts, and the controller together.
 */
public class Building {
    private final List<Floor> floors;
    private final List<Lift> lifts;
    private final ElevatorController controller;

    public Building(int numFloors, int numLifts, double defaultWeightLimitKg,
                    LiftSelectionStrategy selectionStrategy,
                    StopSchedulingStrategy schedulingStrategy) {
        this.floors = new ArrayList<>();
        this.lifts = new ArrayList<>();

        int maxFloor = numFloors - 1;
        for (int i = 0; i <= maxFloor; i++) {
            floors.add(new Floor(i, maxFloor));
        }
        for (int i = 0; i < numLifts; i++) {
            lifts.add(new Lift(i, maxFloor, defaultWeightLimitKg));
        }

        this.controller = new ElevatorController(selectionStrategy, schedulingStrategy);
    }

    /** User presses UP/DOWN on a floor panel. */
    public void callFromFloor(int floorNum, Direction direction) {
        Floor floor = floors.get(floorNum);
        if (direction == Direction.UP && !floor.canCallUp()) {
            System.out.println("Floor " + floorNum + " UP button disabled.");
            return;
        }
        if (direction == Direction.DOWN && !floor.canCallDown()) {
            System.out.println("Floor " + floorNum + " DOWN button disabled.");
            return;
        }
        controller.handleFloorCall(lifts, new FloorRequest(floorNum, direction));
    }

    /** User presses a floor button inside a lift. */
    public void selectFloorInLift(int liftId, int destinationFloor) {
        controller.handleCabinRequest(lifts.get(liftId), destinationFloor);
    }

    /** Advance all lifts by one step. */
    public void tick() {
        for (Lift lift : lifts) {
            controller.step(lift);
        }
    }

    /** Print current status of all lifts. */
    public void printStatus() {
        for (Lift lift : lifts) {
            System.out.println("Lift " + lift.getId()
                    + " | Floor " + lift.getCurrentFloor()
                    + " | " + lift.getState()
                    + " | Door " + (lift.getDoor().isOpen() ? "Open" : "Closed"));
        }
    }

    // --- Admin operations ---
    public void setLiftMaintenance(int liftId, boolean on) {
        lifts.get(liftId).setMaintenance(on);
    }

    public void setFloorMaintenance(int floorNum, boolean on) {
        floors.get(floorNum).setMaintenance(on);
    }

    public void setLiftWeight(int liftId, double kg) {
        lifts.get(liftId).setCurrentWeight(kg);
    }

    /** Set a different weight limit for a specific lift. */
    public void setLiftWeightLimit(int liftId, double kg) {
        lifts.get(liftId).setWeightLimit(kg);
    }

    /** User presses OPEN door button inside a lift. */
    public void pressOpenDoor(int liftId) {
        lifts.get(liftId).getDoor().open();
    }

    /** User presses CLOSE door button inside a lift. */
    public void pressCloseDoor(int liftId) {
        lifts.get(liftId).getDoor().close();
    }

    /** User presses EMERGENCY button inside a lift. */
    public void pressEmergency(int liftId) {
        Lift lift = lifts.get(liftId);
        lift.triggerEmergency();
    }

    public void updateSelectionStrategy(LiftSelectionStrategy strategy) {
        controller.setSelectionStrategy(strategy);
    }

    public List<Lift> getLifts() { return lifts; }
    public List<Floor> getFloors() { return floors; }
}
