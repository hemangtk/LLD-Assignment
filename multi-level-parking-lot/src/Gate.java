public class Gate {
    private final String id;
    private final int floor;
    private final double x;
    private final double y;

    public Gate(String id, int floor, double x, double y) {
        this.id = id;
        this.floor = floor;
        this.x = x;
        this.y = y;
    }

    public String getId() { return id; }
    public int getFloor() { return floor; }
    public double getX() { return x; }
    public double getY() { return y; }
}
