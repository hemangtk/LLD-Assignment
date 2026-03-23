import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Game {
    private final Board board;
    private final Dice dice;
    private final MoveStrategy strategy;
    private final Queue<Player> activePlayers;
    private final List<Player> finishOrder;

    public Game(Board board, Dice dice, MoveStrategy strategy, List<Player> players) {
        this.board = board;
        this.dice = dice;
        this.strategy = strategy;
        this.activePlayers = new LinkedList<>(players);
        this.finishOrder = new ArrayList<>();
    }

    public void start() {
        printBoardSetup();
        System.out.println("\n--- Game Start ---\n");

        while (activePlayers.size() > 1) {
            Player current = activePlayers.poll();
            boolean won = strategy.executeTurn(current, board, dice);

            if (won) {
                current.markFinished();
                finishOrder.add(current);
                System.out.println("*** " + current.getName() + " has WON! (Rank #" + finishOrder.size() + ") ***\n");
            } else {
                activePlayers.add(current);
            }
        }

        // last remaining player
        if (!activePlayers.isEmpty()) {
            Player last = activePlayers.poll();
            finishOrder.add(last);
        }

        printResults();
    }

    private void printBoardSetup() {
        System.out.println("Board size: " + board.getTotalCells() + " cells");
        System.out.println("Snakes: " + board.getSnakes());
        System.out.println("Ladders: " + board.getLadders());
        System.out.println("Players: ");
        for (Player p : activePlayers) {
            System.out.println("  - " + p.getName());
        }
    }

    private void printResults() {
        System.out.println("\n--- Final Rankings ---");
        for (int i = 0; i < finishOrder.size(); i++) {
            System.out.println("#" + (i + 1) + " " + finishOrder.get(i).getName());
        }
    }
}
