public class Slot {
    private final String id;
    private final int floor;
    private final double x;
    private final double y;
    private boolean occupied;

    public Slot(String id, int floor, double x, double y) {
        this.id = id;
        this.floor = floor;
        this.x = x;
        this.y = y;
        this.occupied = false;
    }

    public String getId() { return id; }
    public int getFloor() { return floor; }
    public boolean isOccupied() { return occupied; }

    public void occupy() { this.occupied = true; }
    public void free() { this.occupied = false; }

    public double distanceTo(Gate gate) {
        double dx = this.x - gate.getX();
        double dy = this.y - gate.getY();
        double dz = (this.floor - gate.getFloor()) * 10.0;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    @Override
    public String toString() {
        return id + " (Floor " + floor + ")";
    }
}
