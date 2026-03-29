import java.util.List;

/**
 * Central controller that handles external (floor) and internal (panel) requests.
 * Delegates lift selection and scheduling to pluggable strategies.
 */
public class ElevatorController {
    private LiftSelectionStrategy selectionStrategy;
    private final StopSchedulingStrategy schedulingStrategy;

    public ElevatorController(LiftSelectionStrategy selectionStrategy,
                              StopSchedulingStrategy schedulingStrategy) {
        this.selectionStrategy = selectionStrategy;
        this.schedulingStrategy = schedulingStrategy;
    }

    /**
     * External request: user pressed UP/DOWN on a floor panel.
     * Selects exactly one lift and sends it to that floor.
     */
    public void handleFloorCall(List<Lift> lifts, FloorRequest request) {
        Lift chosen = selectionStrategy.select(lifts, request);
        if (chosen == null) {
            System.out.println("No lift available for floor " + request.getFloor());
            return;
        }
        System.out.println("Lift " + chosen.getId() + " assigned to floor " + request.getFloor());
        chosen.addStop(request.getFloor());
    }

    /**
     * Internal request: user pressed a floor button inside a lift.
     */
    public void handleCabinRequest(Lift lift, int destinationFloor) {
        System.out.println("Lift " + lift.getId() + " got cabin request for floor " + destinationFloor);
        lift.addStop(destinationFloor);
    }

    /**
     * Moves a single lift one step based on the scheduling strategy.
     */
    public void step(Lift lift) {
        if (lift.getState() == LiftState.MAINTENANCE || lift.isEmergency()) return;

        // Overweight check
        if (lift.isOverweight()) {
            System.out.println("  ALARM: Lift " + lift.getId() + " OVERWEIGHT ("
                    + lift.getCurrentWeight() + "/" + lift.getWeightLimit() + " kg) — cannot move!");
            lift.getDoor().open();
            return;
        }

        lift.getDoor().close();

        Direction dir = schedulingStrategy.nextDirection(lift);

        if (dir == null) {
            lift.setState(LiftState.IDLE);
            return;
        }

        if (dir == Direction.UP) {
            lift.setState(LiftState.MOVING_UP);
            lift.setCurrentFloor(lift.getCurrentFloor() + 1);
        } else {
            lift.setState(LiftState.MOVING_DOWN);
            lift.setCurrentFloor(lift.getCurrentFloor() - 1);
        }

        if (lift.hasStopAtCurrent()) {
            lift.removeStopAtCurrent();
            lift.getDoor().open();
            System.out.println("  Lift " + lift.getId() + " stopped at floor " + lift.getCurrentFloor());
        }
    }

    public void setSelectionStrategy(LiftSelectionStrategy strategy) {
        this.selectionStrategy = strategy;
    }
}
