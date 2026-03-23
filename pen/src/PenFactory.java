public class PenFactory {

    public static Pen createPen(PenType type, InkColor color, MechanismType mechanism) {
        WriteStrategy writeStrategy = resolveWriteStrategy(type);
        RefillType refillType = resolveRefillType(type);
        OpenCloseStrategy openCloseStrategy = resolveOpenClose(mechanism);

        Refill refill = new Refill(color, refillType);
        return new Pen(refill, writeStrategy, openCloseStrategy);
    }

    private static WriteStrategy resolveWriteStrategy(PenType type) {
        switch (type) {
            case BALLPOINT: return new BallpointWriteStrategy();
            case GEL:       return new GelWriteStrategy();
            case FOUNTAIN:  return new FountainWriteStrategy();
            default: throw new IllegalArgumentException("Unknown pen type: " + type);
        }
    }

    private static RefillType resolveRefillType(PenType type) {
        switch (type) {
            case BALLPOINT:
            case GEL:       return RefillType.TUBE;
            case FOUNTAIN:  return RefillType.BOTTLE;
            default: throw new IllegalArgumentException("Unknown pen type: " + type);
        }
    }

    private static OpenCloseStrategy resolveOpenClose(MechanismType mechanism) {
        switch (mechanism) {
            case CAP:   return new CapStrategy();
            case CLICK: return new ClickStrategy();
            default: throw new IllegalArgumentException("Unknown mechanism: " + mechanism);
        }
    }
}
