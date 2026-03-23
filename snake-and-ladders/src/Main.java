import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter board dimension n (board will be n x n): ");
        int n = scanner.nextInt();
        if (n < 3) {
            System.out.println("Board dimension must be at least 3.");
            return;
        }

        System.out.print("Enter number of players: ");
        int playerCount = scanner.nextInt();
        if (playerCount < 2) {
            System.out.println("Need at least 2 players.");
            return;
        }
        scanner.nextLine(); // consume newline

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= playerCount; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = scanner.nextLine();
            players.add(new Player(name));
        }

        System.out.print("Enter difficulty level (easy/hard): ");
        String difficulty = scanner.nextLine().trim().toLowerCase();

        MoveStrategy strategy;
        if ("hard".equals(difficulty)) {
            strategy = new HardMoveStrategy();
        } else {
            strategy = new EasyMoveStrategy();
        }

        BoardGenerator generator = new BoardGenerator();
        Board board = generator.generate(n);
        Dice dice = new Dice(6);

        Game game = new Game(board, dice, strategy, players);
        game.start();

        scanner.close();
    }
}
