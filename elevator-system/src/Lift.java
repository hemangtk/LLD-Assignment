import java.util.PriorityQueue;

/**
 * Represents a single elevator car.
 *
 * Maintains two priority queues (upward stops sorted ascending,
 * downward stops sorted descending) so the scheduler can process
 * them in a natural sweep order.
 */
public class Lift {
    private final int id;
    private final int maxFloor;
    private double weightLimit; // in kgs — can vary per cart
    private int currentFloor;
    private LiftState state;
    private final Door door;
    private double currentWeight;
    private boolean emergency;

    // Two queues for directional sweep scheduling
    private final PriorityQueue<Integer> upStops;   // min-heap
    private final PriorityQueue<Integer> downStops;  // max-heap

    public Lift(int id, int maxFloor, double weightLimit) {
        this.id = id;
        this.maxFloor = maxFloor;
        this.weightLimit = weightLimit;
        this.currentFloor = 0;
        this.state = LiftState.IDLE;
        this.door = new Door();
        this.currentWeight = 0;
        this.emergency = false;
        this.upStops = new PriorityQueue<>();
        this.downStops = new PriorityQueue<>((a, b) -> b - a);
    }

    public void addStop(int floor) {
        if (floor == currentFloor) return;
        if (floor > currentFloor) {
            if (!upStops.contains(floor)) upStops.add(floor);
        } else {
            if (!downStops.contains(floor)) downStops.add(floor);
        }
    }

    public boolean hasStopAtCurrent() {
        return (upStops.peek() != null && upStops.peek() == currentFloor)
            || (downStops.peek() != null && downStops.peek() == currentFloor);
    }

    public void removeStopAtCurrent() {
        if (upStops.peek() != null && upStops.peek() == currentFloor) {
            upStops.poll();
        } else if (downStops.peek() != null && downStops.peek() == currentFloor) {
            downStops.poll();
        }
    }

    public boolean hasAnyStops() {
        return !upStops.isEmpty() || !downStops.isEmpty();
    }

    public boolean isOverweight() {
        return currentWeight > weightLimit;
    }

    /** Emergency button pressed — lift stops, door opens, alarm rings. */
    public void triggerEmergency() {
        this.emergency = true;
        this.state = LiftState.MAINTENANCE;
        this.door.open();
        System.out.println("  ALARM: Lift " + id + " emergency triggered! Lift stopped.");
    }

    // --- Maintenance ---
    public void setMaintenance(boolean on) {
        if (on) {
            this.state = LiftState.MAINTENANCE;
            System.out.println("Lift " + id + " is now under maintenance.");
        } else {
            this.state = LiftState.IDLE;
            System.out.println("Lift " + id + " is back in service.");
        }
    }

    // --- Getters & Setters ---
    public int getId() { return id; }
    public int getCurrentFloor() { return currentFloor; }
    public void setCurrentFloor(int f) { this.currentFloor = f; }
    public LiftState getState() { return state; }
    public void setState(LiftState s) { this.state = s; }
    public Door getDoor() { return door; }
    public int getMaxFloor() { return maxFloor; }
    public double getWeightLimit() { return weightLimit; }
    public void setWeightLimit(double wl) { this.weightLimit = wl; }
    public double getCurrentWeight() { return currentWeight; }
    public void setCurrentWeight(double w) { this.currentWeight = w; }
    public boolean isEmergency() { return emergency; }
    public void setEmergency(boolean e) { this.emergency = e; }
    public PriorityQueue<Integer> getUpStops() { return upStops; }
    public PriorityQueue<Integer> getDownStops() { return downStops; }
}
