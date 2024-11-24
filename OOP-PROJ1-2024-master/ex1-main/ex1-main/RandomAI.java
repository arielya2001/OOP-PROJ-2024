import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * RandomAI represents an AI player that selects its moves randomly.
 * It chooses a random valid position on the board and a random disc
 * (if multiple types of discs are available).
 */
public class RandomAI extends AIPlayer {

    /**
     * The Random instance used for generating random choices.
     */
    private Random random;

    /**
     * Constructs a RandomAI instance.
     *
     * @param isPlayerOne true if this AI is Player One, false otherwise.
     */
    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);
        this.random = new Random();
    }

    /**
     * Randomly chooses a disc type to use for the next move.
     * The available disc types include:
     * <ul>
     *     <li>SimpleDisc: Always available.</li>
     *     <li>BombDisc: Available if the player has bombs remaining.</li>
     *     <li>UnflippableDisc: Available if the player has unflippable discs remaining.</li>
     * </ul>
     *
     * @return the chosen Disc to place on the board.
     */
    private Disc chooseRandomDisc() {
        List<Disc> availableDiscs = new ArrayList<>();

        // Always add a SimpleDisc
        availableDiscs.add(new SimpleDisc(this));

        // Add BombDisc if available
        if (this.getNumber_of_bombs() > 0) {
            availableDiscs.add(new BombDisc(this));
        }

        // Add UnflippableDisc if available
        if (this.getNumber_of_unflippedable() > 0) {
            availableDiscs.add(new UnflippableDisc(this));
        }

        // Randomly select one of the available discs
        return availableDiscs.get(random.nextInt(availableDiscs.size()));
    }

    /**
     * Selects a random valid move and a random disc to place.
     * Reduces the count of BombDisc or UnflippableDisc if one is selected.
     *
     * @param gameStatus the current state of the game, providing valid moves.
     * @return the randomly selected move, or null if no valid moves are available.
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> validMoves = gameStatus.ValidMoves();
        if (validMoves.isEmpty()) {
            return null; // No valid moves available
        }

        // Select a random valid position
        int randomIndex = random.nextInt(validMoves.size());
        Position selectedMove = validMoves.get(randomIndex);

        // Choose a random disc to place
        Disc chosenDisc = chooseRandomDisc();

        // Reduce the count for special discs if chosen
        if (chosenDisc instanceof BombDisc) {
            reduce_bomb();
        } else if (chosenDisc instanceof UnflippableDisc) {
            reduce_unflippedable();
        }

        // Return the randomly selected move
        return new Move(selectedMove, chosenDisc, new ArrayList<>(), null); // Pass null for board snapshot
    }
}
