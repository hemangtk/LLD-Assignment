import java.util.List;

public interface SlotAssignmentStrategy {
    Slot findSlot(Gate entryGate, List<Slot> slots);
}
