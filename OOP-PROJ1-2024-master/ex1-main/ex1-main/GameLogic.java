import java.util.*;

public class GameLogic implements PlayableLogic {
    private Disc[][] board = new Disc[8][8];
    private Player player1;
    private Player player2;
    private Stack<Move> moveHistory = new Stack<>();
    private Player currentPlayer;


    private final int[][] DIRECTIONS = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},  // Vertical and Horizontal
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1} // Diagonal
    };

    public GameLogic() {
        initializeBoard();
    }

    private void switchTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    private void initializeBoard() {
        if (player1 == null || player2 == null) return;

        board[3][3] = new SimpleDisc(player1);
        board[3][4] = new SimpleDisc(player2);
        board[4][3] = new SimpleDisc(player2);
        board[4][4] = new SimpleDisc(player1);
        player1.reset_bombs_and_unflippedable();
        player2.reset_bombs_and_unflippedable();
        currentPlayer = player1;

    }

    /**
     * Checks if a given position is within the bounds of the board. It is used to ensure that operations like flips and explosions are only performed within valid positions.
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && col >= 0 && row < board.length && col < board[0].length;
    }

    /**
     * Finds all discs that can be flipped in a specific direction. It checks for a sequence of opponent discs followed by the current player’s disc in a given direction and returns the list of flippable positions.
     */
    private List<Position> getFlippableDiscs(int row, int col, int rowAdd, int colAdd) {
        List<Position> flippable = new ArrayList<>();
        int x = row + rowAdd;
        int y = col + colAdd;

        while (isValidPosition(x, y)) {
            Disc currentDisc = board[x][y];
            if (currentDisc == null) break;

            if (currentDisc.getOwner() != currentPlayer) {
                flippable.add(new Position(x, y));
            } else {
                return flippable;
            }
            x += rowAdd;
            y += colAdd;
        }
        return new ArrayList<>();
    }

    /**
     *Determines if placing a disc at a given position is valid. It checks that the position is empty and that flipping is possible in at least one direction.
     */
    private boolean isValidMove(int row, int col, Disc disc) {
        if (!isValidPosition(row, col) || board[row][col] != null) return false;
        for (int[] dir : DIRECTIONS) {
            if (!getFlippableDiscs(row, col, dir[0], dir[1]).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Handles chain reactions when a BombDisc is flipped. It flips surrounding discs and triggers further explosions for adjacent BombDisc objects, updating the board and maintaining the sets of flipped and processed positions.
     */
    private void triggerExplosion(int row, int col, Player owner, Set<Position> flippedPositions, Set<Position> triggeredBombs) {
        Queue<Position> queue = new LinkedList<>();
        Set<Position> processedPositions = new HashSet<>();

        Position startPos = new Position(row, col);
        queue.add(startPos);
        triggeredBombs.add(startPos);

        while (!queue.isEmpty()) {
            Position currentPos = queue.poll();
            processedPositions.add(currentPos);

            for (int[] dir : DIRECTIONS) {
                int adjRow = currentPos.getRow() + dir[0];
                int adjCol = currentPos.getCol() + dir[1];

                if (isValidPosition(adjRow, adjCol)) {
                    Position adjPos = new Position(adjRow, adjCol);
                    if (processedPositions.contains(adjPos)) continue;

                    Disc adjDisc = board[adjRow][adjCol];

                    if (adjDisc instanceof UnflippableDisc) continue;
                    if (adjDisc != null && !flippedPositions.contains(adjPos)) {
                        adjDisc.setOwner(owner);
                        flippedPositions.add(adjPos);
                        processedPositions.add(adjPos);

                        if (adjDisc instanceof BombDisc && !triggeredBombs.contains(adjPos)) {
                            queue.add(adjPos);
                            triggeredBombs.add(adjPos);
                        }
                    }
                }
            }
        }
    }


    /**
     * Flips discs in all valid directions starting from a placed disc. It updates the ownership of flipped discs, triggers explosions for BombDisc, and logs the flipping actions to the console.
     */
    private List<Position> flipDiscs(int row, int col, Disc disc,int numOfPlayer) {
        Set<Position> flippedPositions = new HashSet<>();
        Set<Position> triggeredBombs = new HashSet<>();

        for (int[] dir : DIRECTIONS) {
            List<Position> flippable = getFlippableDiscs(row, col, dir[0], dir[1]);
            for (Position pos : flippable) {
                Disc flippedDisc = board[pos.getRow()][pos.getCol()];
                if (flippedDisc != null && !(flippedDisc instanceof UnflippableDisc) && flippedDisc.getOwner() != disc.getOwner()) {
                    flippedDisc.setOwner(disc.getOwner());
                    flippedPositions.add(pos);
                    if (flippedDisc instanceof BombDisc && !triggeredBombs.contains(pos)) {
                        triggerExplosion(pos.getRow(), pos.getCol(), disc.getOwner(), flippedPositions, triggeredBombs);
                    }
                }
            }
        }
        for (Position pos : flippedPositions) {
            if (pos.getRow() == row && pos.getCol() == col) {
                continue;
            }
            Disc flippedDisc = board[pos.getRow()][pos.getCol()];
            System.out.println("Player " + numOfPlayer + " flipped the " + flippedDisc.getType() +
                    " in " + "(" + (pos.getRow() + 1) + " , " + (pos.getCol() + 1) + ")");
        }

        return new ArrayList<>(flippedPositions);
    }



    @Override
    public boolean locate_disc(Position a, Disc disc) {
        if (disc instanceof BombDisc) {
            if (currentPlayer.getNumber_of_bombs() <= 0) {
//                System.out.println("No bombs left for player: " + currentPlayer);
                return false;
            }
            currentPlayer.reduce_bomb();
        } else if (disc instanceof UnflippableDisc) {
            if (currentPlayer.getNumber_of_unflippedable() <= 0) {
                System.out.println("No unflippable discs left for player: " + currentPlayer);
                return false;
            }
            currentPlayer.reduce_unflippedable();
        }

        int row = a.getRow();
        int col = a.getCol();
        if (!isValidMove(row, col, disc)) return false;

        Disc[][] boardSnapshot = deepCopyBoard();
        int numOfPlayer=(currentPlayer==player1)?1:2;
        board[row][col] = disc;
        System.out.println("Player " +numOfPlayer+ " placed a " +disc.getType()+" in " + "("+(row+1)+" , "+(col+1)+")");
        List<Position> flippedPositions = flipDiscs(row, col, disc,numOfPlayer);
        moveHistory.push(new Move(a, disc, flippedPositions, boardSnapshot, currentPlayer));
        switchTurn();
        System.out.println();
        return true;
    }

    /**
     * Creates a deep copy of the current board state. It is used to store snapshots of the board for undo functionality, ensuring that each snapshot reflects the exact state of the board at a given point.
     */
    private Disc[][] deepCopyBoard() {
        Disc[][] copy = new Disc[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    if (board[i][j] instanceof SimpleDisc) {
                        copy[i][j] = new SimpleDisc(board[i][j].getOwner());
                    } else if (board[i][j] instanceof BombDisc) {
                        copy[i][j] = new BombDisc(board[i][j].getOwner());
                    } else if (board[i][j] instanceof UnflippableDisc) {
                        copy[i][j] = new UnflippableDisc(board[i][j].getOwner());
                    }
                }
            }
        }
        return copy;
    }



    @Override
    public Disc getDiscAtPosition(Position position) {
        return board[position.getRow()][position.getCol()];
    }

    @Override
    public int getBoardSize() {
        return board.length;
    }

    @Override
    public List<Position> ValidMoves() {
        List<Position> validMoves = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                Position pos = new Position(row, col);
                if (isValidMove(row, col, new SimpleDisc(currentPlayer)) && countFlips(pos) > 0) {
                    validMoves.add(pos);
                }
            }
        }
        return validMoves;
    }

    /**
     *Simulates the chain reaction caused by a BombDisc. It flips adjacent discs, adds them to the flipped set, and continues the process for any adjacent BombDisc objects.
     */
    private void simulateBombExplosion(int row, int col, Set<Position> flippedPositions, Set<Position> processedBombs) {
        Queue<Position> queue = new LinkedList<>();
        queue.add(new Position(row, col));

        while (!queue.isEmpty()) {
            Position current = queue.poll();

            if (processedBombs.contains(current)) continue;
            processedBombs.add(current);

            for (int[] dir : DIRECTIONS) {
                int adjRow = current.getRow() + dir[0];
                int adjCol = current.getCol() + dir[1];

                if (isValidPosition(adjRow, adjCol)) {
                    Position adjPos = new Position(adjRow, adjCol);
                    Disc adjDisc = board[adjRow][adjCol];

                    if (adjDisc != null && !(adjDisc instanceof UnflippableDisc) && !flippedPositions.contains(adjPos) && adjDisc.getOwner() != currentPlayer) {
                        flippedPositions.add(adjPos);

                        if (adjDisc instanceof BombDisc) {
                            queue.add(adjPos);
                        }
                    }
                }
            }
        }
    }

    /**
     * Counts the discs on the board to determine the winner when the game ends. It prints the results, including the number of discs each player has and announces the winner or a tie.
     */
    private void winnerCount() {
        int countPlayer1 = 0;
        int countPlayer2 = 0;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                Disc disc = board[row][col];
                if (disc != null) {
                    if (disc.getOwner() == player1) {
                        countPlayer1++;
                    } else if (disc.getOwner() == player2) {
                        countPlayer2++;
                    }
                }
            }
        }
        int totalDiscs = countPlayer1 + countPlayer2;
        if (countPlayer1 > countPlayer2) {
            player1.addWin();
            System.out.println("Player 1 wins with " + countPlayer1 + " discs! Player 2 had " + countPlayer2 + " discs.");
        } else if (countPlayer2 > countPlayer1) {
            player2.addWin();
            System.out.println("Player 2 wins with " + countPlayer2 + " discs! Player 1 had " + countPlayer1 + " discs.");
        } else {
            System.out.println("The game is a tie! Both players have " + countPlayer1 + " discs.");
        }
        System.out.println("Total discs on the board: " + totalDiscs + "\n");
    }

    @Override
    public int countFlips(Position pos) {
        Set<Position> flippedPositions = new HashSet<>();
        Set<Position> processedBombs = new HashSet<>();

        for (int[] dir : DIRECTIONS) {
            List<Position> flippable = getFlippableDiscs(pos.getRow(), pos.getCol(), dir[0], dir[1]);
            for (Position p : flippable) {
                if (flippedPositions.contains(p)) continue;

                Disc disc = board[p.getRow()][p.getCol()];
                if (disc != null && !(disc instanceof UnflippableDisc) && disc.getOwner() != currentPlayer) {
                    flippedPositions.add(p);

                    if (disc instanceof BombDisc && !processedBombs.contains(p)) {
                        simulateBombExplosion(p.getRow(), p.getCol(), flippedPositions, processedBombs);
                    }
                }
            }
        }

        return flippedPositions.size();
    }


    @Override
    public Player getFirstPlayer() {
        return player1;
    }

    @Override
    public Player getSecondPlayer() {
        return player2;
    }

    @Override
    public void setPlayers(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        currentPlayer = player1;
        initializeBoard();
    }

    @Override
    public boolean isFirstPlayerTurn() {
        return currentPlayer == player1;
    }

    @Override
    public boolean isGameFinished() {
        boolean isBoardFull = true;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == null) {
                    isBoardFull = false;
                    break;
                }
            }
            if (!isBoardFull) break;
        }
        if (isBoardFull) {
            winnerCount();
            return true;
        }
        if (ValidMoves().isEmpty()) {
            winnerCount();
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        for (int i = 0; i < board.length; i++) {
            Arrays.fill(board[i], null);
        }
        moveHistory.clear();
        player1.reduce_bomb();
        player2.reduce_bomb();
        player1.reduce_unflippedable();
        player2.reduce_unflippedable();
        initializeBoard();
    }

    @Override
    public void undoLastMove() {
        if (moveHistory.isEmpty()) {
            System.out.println("No previous move available to undo\n");
            return;
        }

        System.out.println("Undoing last move : ");

        Move lastMove = moveHistory.pop();
        Position pos = lastMove.getPosition();
        Disc placedDisc = lastMove.getPlacedDisc();
        Player originalPlayer = lastMove.getOriginalPlayer();

        restoreBoardFromSnapshot(lastMove.getBoardSnapshot());

        if (placedDisc instanceof BombDisc) {
            originalPlayer.number_of_bombs++;
        } else if (placedDisc instanceof UnflippableDisc) {
            originalPlayer.number_of_unflippedable++;
        }
        System.out.println("removing " + placedDisc.getType() + " from (" +
                (pos.getRow() + 1) + ", " + (pos.getCol() + 1) + ")\n");

        for (Position flippedPos : lastMove.getFlippedPositions()) {
            Disc flippedDisc = board[flippedPos.getRow()][flippedPos.getCol()];
            if (flippedDisc == null)
                continue;
            System.out.println("\tUndo: flipping back " + flippedDisc.getType() + " in (" +
                    (flippedPos.getRow() + 1) + ", " + (flippedPos.getCol() + 1) + ")\n");
        }
        switchTurn();
    }

    /**
     * Restores the board to a previous state from a snapshot. It is used during the undo process to revert the board to the state it was in before the last move.
     */
    private void restoreBoardFromSnapshot(Disc[][] snapshot) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = snapshot[i][j];
            }
        }
    }
}
