public class HardMoveStrategy implements MoveStrategy {

    private static final int MAX_CONSECUTIVE_SIXES = 3;

    @Override
    public boolean executeTurn(Player player, Board board, Dice dice) {
        int consecutiveSixes = 0;

        while (true) {
            int roll = dice.roll();
            System.out.println(player.getName() + " rolled a " + roll);

            if (roll == 6) {
                consecutiveSixes++;
                if (consecutiveSixes >= MAX_CONSECUTIVE_SIXES) {
                    System.out.println("  Three 6s in a row! Turn forfeited.");
                    return false;
                }
                System.out.println("  Rolled a 6! Extra turn granted.");
            }

            int next = player.getPosition() + roll;

            if (next > board.getTotalCells()) {
                System.out.println("  Can't move beyond the board. Stay at " + player.getPosition());
            } else {
                next = board.resolve(next);
                player.moveTo(next);
                System.out.println("  " + player.getName() + " is now at position " + next);

                if (next == board.getTotalCells()) {
                    return true;
                }
            }

            if (roll != 6) {
                break;
            }
        }
        return false;
    }
}
