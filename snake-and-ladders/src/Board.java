import java.util.Collections;
import java.util.Map;

public class Board {
    private final int totalCells;
    private final Map<Integer, Integer> snakes;   // head -> tail
    private final Map<Integer, Integer> ladders;  // bottom -> top

    public Board(int totalCells, Map<Integer, Integer> snakes, Map<Integer, Integer> ladders) {
        this.totalCells = totalCells;
        this.snakes = snakes;
        this.ladders = ladders;
    }

    public int getTotalCells() {
        return totalCells;
    }

    public int resolve(int position) {
        if (snakes.containsKey(position)) {
            int tail = snakes.get(position);
            System.out.println("  Oops! Snake at " + position + " -> slides down to " + tail);
            return tail;
        }
        if (ladders.containsKey(position)) {
            int top = ladders.get(position);
            System.out.println("  Yay! Ladder at " + position + " -> climbs up to " + top);
            return top;
        }
        return position;
    }

    public Map<Integer, Integer> getSnakes() {
        return Collections.unmodifiableMap(snakes);
    }

    public Map<Integer, Integer> getLadders() {
        return Collections.unmodifiableMap(ladders);
    }
}
