public class EasyMoveStrategy implements MoveStrategy {

    @Override
    public boolean executeTurn(Player player, Board board, Dice dice) {
        int roll = dice.roll();
        System.out.println(player.getName() + " rolled a " + roll);

        int next = player.getPosition() + roll;

        if (next > board.getTotalCells()) {
            System.out.println("  Can't move beyond the board. Stay at " + player.getPosition());
            return false;
        }

        next = board.resolve(next);
        player.moveTo(next);
        System.out.println("  " + player.getName() + " is now at position " + next);

        return next == board.getTotalCells();
    }
}
