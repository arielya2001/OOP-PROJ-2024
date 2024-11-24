/**
 * Represents a simple disc in the game. This disc has no special behavior
 * and is the default type of disc placed on the board during a move.
 */
public class SimpleDisc implements Disc {

    /**
     * The player who owns this disc.
     */
    private Player owner;

    /**
     * Constructs a SimpleDisc with the specified owner.
     *
     * @param currentPlayer the player who owns this disc.
     */
    public SimpleDisc(Player currentPlayer) {
        this.owner = currentPlayer;
    }

    /**
     * Gets the owner of this disc.
     *
     * @return the player who owns this disc.
     */
    @Override
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Sets the owner of this disc.
     *
     * @param player the new owner of this disc.
     */
    @Override
    public void setOwner(Player player) {
        this.owner = player;
    }

    /**
     * Returns the type of this disc as a string.
     * The type is represented by a filled circle symbol ("⬤").
     *
     * @return the string representation of this disc type.
     */
    @Override
    public String getType() {
        return "⬤";
    }

    /**
     * Creates a copy of this SimpleDisc with the same owner.
     *
     * @return a new SimpleDisc instance with the same owner.
     */
    public SimpleDisc copy() {
        return new SimpleDisc(this.owner);
    }
}
