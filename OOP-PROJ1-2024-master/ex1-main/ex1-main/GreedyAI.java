import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * GreedyAI is an AIPlayer implementation for a game. This AI player
 * selects its moves based on a "greedy" strategy, choosing the move
 * that maximizes the number of flipped opponent discs. In case of ties,
 * it prefers moves in the rightmost column, and if there's still a tie,
 * the bottommost row is chosen.
 */
public class GreedyAI extends AIPlayer {

    /**
     * Constructs a GreedyAI instance.
     *
     * @param isPlayerOne true if this AI is Player One, false otherwise.
     */
    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    /**
     * Determines and returns the best possible move based on the greedy strategy.
     * The move is selected by maximizing the number of flipped opponent discs.
     * If there are multiple moves with the same number of flips, the move
     * in the rightmost column is preferred, and then the move in the bottommost
     * row if there's still a tie.
     *
     * @param gameStatus the current state of the game, which provides valid moves
     *                   and flipping counts.
     * @return the chosen move, or null if no valid moves are available.
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> validMoves = gameStatus.ValidMoves();
        if (validMoves.isEmpty()) {
            return null;
        }

        // Define a comparator to evaluate moves based on the greedy strategy
        Comparator<Position> moveComparator = (p1, p2) -> {
            int flipsComparison = Integer.compare(gameStatus.countFlips(p1), gameStatus.countFlips(p2));
            if (flipsComparison != 0) {
                return flipsComparison; // Compare based on number of flips
            }
            int colComparison = Integer.compare(p1.getCol(), p2.getCol());
            if (colComparison != 0) {
                return colComparison; // Compare based on column (rightmost)
            }
            return Integer.compare(p1.getRow(), p2.getRow()); // Compare based on row (bottommost)
        };

        // Find the best move using a traditional loop
        Position bestMove = validMoves.get(0);
        for (Position move : validMoves) {
            if (moveComparator.compare(move, bestMove) > 0) {
                bestMove = move;
            }
        }

        // Create a SimpleDisc for the chosen move
        Disc chosenDisc = new SimpleDisc(this);

        // Return the chosen move, with a null board snapshot as this is not required
        return new Move(bestMove, chosenDisc, new ArrayList<>(), null);
    }
}
