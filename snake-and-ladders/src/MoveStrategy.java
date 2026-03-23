public interface MoveStrategy {
    /**
     * Execute a player's turn.
     * @return true if the player reached the final cell and wins.
     */
    boolean executeTurn(Player player, Board board, Dice dice);
}
