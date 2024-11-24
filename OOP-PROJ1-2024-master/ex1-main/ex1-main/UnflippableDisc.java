/**
 * Represents an unflippable disc in the game.
 * This type of disc cannot be flipped by an opponent's move,
 * providing a strategic advantage to the player who places it.
 */
public class UnflippableDisc implements Disc {

    /**
     * The player who owns this disc.
     */
    private Player owner;

    /**
     * Constructs an UnflippableDisc with the specified owner.
     *
     * @param currentPlayer the player who owns this disc.
     */
    public UnflippableDisc(Player currentPlayer) {
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
     * The type is represented by an unfilled circle symbol ("⭕").
     *
     * @return the string representation of this disc type.
     */
    @Override
    public String getType() {
        return "⭕";
    }

    /**
     * Creates a copy of this UnflippableDisc with the same owner.
     *
     * @return a new UnflippableDisc instance with the same owner.
     */
    public UnflippableDisc copy() {
        return new UnflippableDisc(this.owner);
    }
}
