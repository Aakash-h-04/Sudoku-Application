package application.player;

/**
 * Represents the current cursor position on the Sudoku board.
 *
 * Responsibility:
 *  - Maintain cursor position.
 *  - Handle cursor movement.
 *  - Prevent moving outside the board.
 */
public class Cursor {

    private int row;
    private int col;

    private final int size;

    /**
     * Creates a cursor positioned at the top-left cell.
     *
     * @param boardSize Sudoku board size.
     */
    public Cursor(int boardSize) {

        if (boardSize <= 0) {
            throw new IllegalArgumentException(
                    "Board size must be positive.");
        }

        this.size = boardSize;

        row = 0;
        col = 0;
    }

    /**
     * Returns current row.
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns current column.
     */
    public int getCol() {
        return col;
    }

    /**
     * Moves the cursor according to the given command.
     *
     * Supported commands:
     *
     * Q W E
     * A   D
     * Z X C
     *
     * @param command movement command
     * @return true if movement succeeds,
     *         false otherwise.
     */
    public boolean move(char command) {

        command = Character.toLowerCase(command);

        int nextRow = row;
        int nextCol = col;

        switch (command) {

            case 'q':
                nextRow--;
                nextCol--;
                break;

            case 'w':
                nextRow--;
                break;

            case 'e':
                nextRow--;
                nextCol++;
                break;

            case 'a':
                nextCol--;
                break;

            case 'd':
                nextCol++;
                break;

            case 'z':
                nextRow++;
                nextCol--;
                break;

            case 'x':
                nextRow++;
                break;

            case 'c':
                nextRow++;
                nextCol++;
                break;

            default:
                return false;
        }

        if (!isInside(nextRow, nextCol)) {
            return false;
        }

        row = nextRow;
        col = nextCol;

        return true;
    }

    /**
     * Returns true if the given position lies inside
     * the Sudoku board.
     */
    private boolean isInside(int row, int col) {

        return row >= 0
                && row < size
                && col >= 0
                && col < size;
    }

}