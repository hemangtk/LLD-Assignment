/**
 * Represents a floor in the building.
 * Has up/down call buttons (null when not applicable, e.g., no DOWN on ground floor).
 * Can be put under maintenance to disable its buttons.
 */
public class Floor {
    private final int number;
    private final boolean hasUpButton;
    private final boolean hasDownButton;
    private boolean underMaintenance;

    public Floor(int number, int maxFloor) {
        this.number = number;
        this.hasUpButton = (number < maxFloor);
        this.hasDownButton = (number > 0);
        this.underMaintenance = false;
    }

    public boolean canCallUp() {
        return hasUpButton && !underMaintenance;
    }

    public boolean canCallDown() {
        return hasDownButton && !underMaintenance;
    }

    public void setMaintenance(boolean on) {
        this.underMaintenance = on;
        System.out.println("Floor " + number + (on ? " under maintenance — buttons disabled." : " back in service."));
    }

    public int getNumber() { return number; }
    public boolean isUnderMaintenance() { return underMaintenance; }
}
