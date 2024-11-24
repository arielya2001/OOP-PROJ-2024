import java.util.Objects;

/**
 * Represents a position on the game board with row and column indices.
 * This class is immutable and provides methods for accessing the row and column values.
 */
public class Position {
    /**
     * The row index of the position.
     */
    private final int row;

    /**
     * The column index of the position.
     */
    private final int col;

    /**
     * Constructs a new Position with the specified row and column.
     *
     * @param row the row index of the position.
     * @param col the column index of the position.
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Gets the row index of this position.
     *
     * @return the row index.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column index of this position.
     *
     * @return the column index.
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets the row index of this position.
     * This method is required by the GUI.
     *
     * @return the row index.
     */
    public int row() {
        return row;
    }

    /**
     * Gets the column index of this position.
     * This method is required by the GUI.
     *
     * @return the column index.
     */
    public int col() {
        return col;
    }

    /**
     * Checks if this position is equal to another object.
     * Two positions are considered equal if their row and column indices are the same.
     *
     * @param o the object to compare with this position.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    /**
     * Computes the hash code for this position.
     * The hash code is based on the row and column indices.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    /**
     * Returns a string representation of this position in the format "(row, col)".
     *
     * @return a string representing this position.
     */
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
