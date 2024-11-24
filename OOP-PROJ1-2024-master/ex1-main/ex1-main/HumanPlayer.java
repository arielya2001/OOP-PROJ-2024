/**
 * HumanPlayer represents a human player in the game.
 * This class extends the abstract Player class and specifies that this player is controlled by a human.
 */
public class HumanPlayer extends Player {

    /**
     * Constructs a HumanPlayer instance.
     *
     * @param isPlayerOne true if this player is Player One, false if Player Two.
     */
    public HumanPlayer(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    /**
     * Indicates that this player is a human.
     *
     * @return true, as this class represents a human player.
     */
    @Override
    boolean isHuman() {
        return true;
    }
}
