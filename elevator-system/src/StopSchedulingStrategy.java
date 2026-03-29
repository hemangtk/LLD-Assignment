/**
 * Strategy that decides the next direction a lift should move.
 * This controls the order in which stops are served.
 */
public interface StopSchedulingStrategy {
    Direction nextDirection(Lift lift);
}
