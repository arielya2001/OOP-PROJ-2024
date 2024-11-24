/**
 * Represents a Bomb Disc in the game. This type of disc has special behavior
 * when placed on the board, such as potentially triggering additional effects.
 * The owner of the disc is the player who placed it.
 */
public class BombDisc implements Disc {

    /**
     * The player who owns this bomb disc.
     */
    private Player owner;

    /**
     * Constructs a new BombDisc with the specified owner.
     *
     * @param currentPlayer the player who owns this disc.
     */
    public BombDisc(Player currentPlayer) {
        this.owner = currentPlayer;
    }

    /**
     * Gets the owner of this bomb disc.
     *
     * @return the player who owns this disc.
     */
    @Override
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Sets the owner of this bomb disc.
     *
     * @param player the new owner of this disc.
     */
    @Override
    public void setOwner(Player player) {
        this.owner = player;
    }

    /**
     * Returns the type of this disc as a string.
     * This type is represented by a bomb emoji ("💣").
     *
     * @return the string representation of this disc type.
     */
    @Override
    public String getType() {
        return "💣";
    }

    /**
     * Creates a copy of this BombDisc with the same owner.
     *
     * @return a new BombDisc instance with the same owner.
     */
    public BombDisc copy() {
        return new BombDisc(owner);
    }
}
