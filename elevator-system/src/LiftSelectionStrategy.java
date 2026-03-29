import java.util.List;

/**
 * Strategy for choosing which lift to send for a floor request.
 * Swap implementations without changing any other class.
 */
public interface LiftSelectionStrategy {
    Lift select(List<Lift> lifts, FloorRequest request);
}
