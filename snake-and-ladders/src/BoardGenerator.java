import java.util.*;

public class BoardGenerator {

    private final Random random = new Random();

    public Board generate(int n) {
        if (n < 3) {
            throw new IllegalArgumentException("Board dimension must be at least 3.");
        }
        int totalCells = n * n;
        Map<Integer, Integer> snakes = new HashMap<>();
        Map<Integer, Integer> ladders = new HashMap<>();
        Set<Integer> occupied = new HashSet<>();

        // cell 1 and totalCells must remain free
        occupied.add(1);
        occupied.add(totalCells);

        placeSnakes(n, totalCells, snakes, occupied);
        placeLadders(n, totalCells, ladders, snakes, occupied);

        return new Board(totalCells, snakes, ladders);
    }

    private void placeSnakes(int count, int totalCells, Map<Integer, Integer> snakes, Set<Integer> occupied) {
        int placed = 0;
        int maxAttempts = count * 50;
        int attempts = 0;

        while (placed < count && attempts < maxAttempts) {
            attempts++;
            int head = 2 + random.nextInt(totalCells - 2); // [2, totalCells-1]
            int tail = 1 + random.nextInt(head - 1);       // [1, head-1]

            if (occupied.contains(head) || occupied.contains(tail)) continue;

            snakes.put(head, tail);
            occupied.add(head);
            occupied.add(tail);
            placed++;
        }
    }

    private void placeLadders(int count, int totalCells, Map<Integer, Integer> ladders,
                              Map<Integer, Integer> snakes, Set<Integer> occupied) {
        int placed = 0;
        int maxAttempts = count * 50;
        int attempts = 0;

        while (placed < count && attempts < maxAttempts) {
            attempts++;
            int bottom = 2 + random.nextInt(totalCells - 2); // [2, totalCells-1]
            int top = bottom + 1 + random.nextInt(totalCells - bottom); // (bottom, totalCells]

            if (occupied.contains(bottom) || occupied.contains(top)) continue;

            // Cycle check: a ladder top must not land on a snake head that leads back
            // to a position <= bottom (which could create infinite loops)
            if (wouldCreateCycle(top, bottom, snakes, ladders)) continue;

            ladders.put(bottom, top);
            occupied.add(bottom);
            occupied.add(top);
            placed++;
        }
    }

    private boolean wouldCreateCycle(int landingPos, int origin,
                                     Map<Integer, Integer> snakes, Map<Integer, Integer> ladders) {
        Set<Integer> visited = new HashSet<>();
        int current = landingPos;

        while (true) {
            if (!visited.add(current)) return true; // cycle detected

            if (snakes.containsKey(current)) {
                current = snakes.get(current);
            } else if (ladders.containsKey(current)) {
                current = ladders.get(current);
            } else {
                break;
            }

            if (current == origin) return true;
        }
        return false;
    }
}
