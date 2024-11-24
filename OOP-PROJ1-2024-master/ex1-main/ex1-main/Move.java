import java.util.List;

/**
 * Represents a move made during the game.
 * A move includes the position where a disc was placed, the disc itself,
 * the positions of discs flipped as a result of the move, an optional
 * board snapshot, and the player who originally made the move.
 */
public class Move {

    /**
     * The position where the disc was placed.
     */
    private final Position position;

    /**
     * The disc placed during this move.
     */
    private final Disc placedDisc;

    /**
     * The list of positions of discs flipped as a result of this move.
     */
    private final List<Position> flippedPositions;

    /**
     * An optional snapshot of the board's state after the move.
     * This may be null if no snapshot was taken.
     */
    private final Disc[][] boardSnapshot;

    /**
     * The player who made this move.
     */
    private final Player originalPlayer;

    /**
     * Constructs a Move without a board snapshot.
     *
     * @param position the position where the disc was placed.
     * @param placedDisc the disc placed during the move.
     * @param flippedPositions the positions of discs flipped as a result of the move.
     * @param originalPlayer the player who made the move.
     */
    public Move(Position position, Disc placedDisc, List<Position> flippedPositions, Player originalPlayer) {
        this(position, placedDisc, flippedPositions, null, originalPlayer); // Default boardSnapshot to null
    }

    /**
     * Constructs a Move with a specified board snapshot.
     *
     * @param position the position where the disc was placed.
     * @param placedDisc the disc placed during the move.
     * @param flippedPositions the positions of discs flipped as a result of the move.
     * @param boardSnapshot an optional snapshot of the board's state after the move.
     * @param originalPlayer the player who made the move.
     */
    public Move(Position position, Disc placedDisc, List<Position> flippedPositions, Disc[][] boardSnapshot, Player originalPlayer) {
        this.position = position;
        this.placedDisc = placedDisc;
        this.flippedPositions = flippedPositions;
        this.boardSnapshot = boardSnapshot;
        this.originalPlayer = originalPlayer;
    }

    /**
     * Gets the player who made this move.
     *
     * @return the player who made the move.
     */
    public Player getOriginalPlayer() {
        return originalPlayer;
    }

    /**
     * Gets the position where the disc was placed.
     *
     * @return the position of the move.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Gets the disc that was placed during this move.
     *
     * @return the disc placed during the move.
     */
    public Disc getPlacedDisc() {
        return placedDisc;
    }

    /**
     * Gets the positions of discs flipped as a result of this move.
     *
     * @return a list of flipped positions.
     */
    public List<Position> getFlippedPositions() {
        return flippedPositions;
    }

    /**
     * Gets the board snapshot after the move.
     *
     * @return a 2D array representing the board's state, or null if no snapshot was taken.
     */
    public Disc[][] getBoardSnapshot() {
        return boardSnapshot;
    }

    /**
     * Gets the position of the move.
     * This method is required by the GUI.
     *
     * @return the position of the move.
     */
    public Position position() {
        return getPosition();
    }

    /**
     * Gets the disc placed during this move.
     * This method is required by the GUI.
     *
     * @return the disc placed during the move.
     */
    public Disc disc() {
        return getPlacedDisc();
    }
}
