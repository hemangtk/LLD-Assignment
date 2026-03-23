public class Refill {
    private final InkColor color;
    private final RefillType type;

    public Refill(InkColor color, RefillType type) {
        this.color = color;
        this.type = type;
    }

    public InkColor getColor() { return color; }
    public RefillType getType() { return type; }
}
