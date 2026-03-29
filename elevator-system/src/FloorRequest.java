/**
 * A request made from a floor panel (external) — user presses UP or DOWN.
 */
public class FloorRequest {
    private final int floor;
    private final Direction direction;

    public FloorRequest(int floor, Direction direction) {
        this.floor = floor;
        this.direction = direction;
    }

    public int getFloor() { return floor; }
    public Direction getDirection() { return direction; }
}
