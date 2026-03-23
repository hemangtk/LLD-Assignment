import java.util.List;

public class NearestSlotStrategy implements SlotAssignmentStrategy {

    @Override
    public Slot findSlot(Gate entryGate, List<Slot> slots) {
        Slot nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Slot slot : slots) {
            if (!slot.isOccupied()) {
                double distance = slot.distanceTo(entryGate);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = slot;
                }
            }
        }
        return nearest;
    }
}
