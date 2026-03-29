import java.util.List;

/**
 * Picks the closest non-maintenance, non-emergency lift.
 */
public class NearestLiftStrategy implements LiftSelectionStrategy {

    @Override
    public Lift select(List<Lift> lifts, FloorRequest request) {
        Lift best = null;
        int bestDist = Integer.MAX_VALUE;

        for (Lift lift : lifts) {
            if (lift.getState() == LiftState.MAINTENANCE || lift.isEmergency()) continue;

            int dist = Math.abs(lift.getCurrentFloor() - request.getFloor());
            if (dist < bestDist) {
                bestDist = dist;
                best = lift;
            }
        }
        return best;
    }
}
