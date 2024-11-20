import java.util.List;

public class Move {
    private final Position position;
    private final Disc placedDisc;
    private final List<Position> flippedPositions;
    private final Disc[][] boardSnapshot;
    private final Player originalPlayer;


    public Move(Position position, Disc placedDisc, List<Position> flippedPositions, Player originalPlayer) {
        this(position, placedDisc, flippedPositions, null, originalPlayer); // Default boardSnapshot to null
    }

    public Move(Position position, Disc placedDisc, List<Position> flippedPositions, Disc[][] boardSnapshot, Player originalPlayer) {
        this.position = position;
        this.placedDisc = placedDisc;
        this.flippedPositions = flippedPositions;
        this.boardSnapshot = boardSnapshot;
        this.originalPlayer = originalPlayer;
    }
    public Player getOriginalPlayer() {
        return originalPlayer;
    }

    public Position getPosition() {
        return position;
    }

    public Disc getPlacedDisc() {
        return placedDisc;
    }

    public List<Position> getFlippedPositions() {
        return flippedPositions;
    }

    public Disc[][] getBoardSnapshot() {
        return boardSnapshot;
    }

    public Position position() { // Required by the GUI
        return getPosition();
    }

    public Disc disc() { // Required by the GUI
        return getPlacedDisc();
    }
}
