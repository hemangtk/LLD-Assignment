/**
 * Classic SCAN (sweep) algorithm:
 *  - Continue in current direction while stops remain that way.
 *  - Otherwise switch direction.
 *  - If no stops at all, return null (idle).
 *
 *  Example: lift at floor 10 going UP with stops [20, 15, 2, 0]
 *    -> serves 15, 20 first (UP), then sweeps DOWN to 2, 0.
 */
public class SweepSchedulingStrategy implements StopSchedulingStrategy {

    @Override
    public Direction nextDirection(Lift lift) {
        boolean hasUp = !lift.getUpStops().isEmpty();
        boolean hasDown = !lift.getDownStops().isEmpty();

        if (!hasUp && !hasDown) return null;

        LiftState state = lift.getState();

        if (state == LiftState.MOVING_UP) {
            return hasUp ? Direction.UP : Direction.DOWN;
        } else if (state == LiftState.MOVING_DOWN) {
            return hasDown ? Direction.DOWN : Direction.UP;
        } else {
            // IDLE — pick whichever has stops
            return hasUp ? Direction.UP : Direction.DOWN;
        }
    }
}
