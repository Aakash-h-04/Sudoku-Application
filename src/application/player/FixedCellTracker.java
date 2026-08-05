package application.player;

import domain.SudokuBoard;

/**
 * Tracks the immutable (fixed) cells of a Sudoku puzzle.
 *
 * Fixed cells are the original clues supplied by the puzzle
 * and cannot be modified by the player.
 *
 * This class belongs to the Player module because only the
 * interactive game needs to know which cells are fixed.
 */
public final class FixedCellTracker {

    private final boolean[][] fixed;

    /**
     * Creates the tracker from the initial Sudoku board.
     */
    public FixedCellTracker(SudokuBoard board) {

        int size = board.getSize();

        fixed = new boolean[size][size];

        initialize(board);
    }

    /**
     * Marks every non-empty cell as fixed.
     */
    private void initialize(SudokuBoard board) {

        int size = board.getSize();

        for (int row = 0; row < size; row++) {

            for (int col = 0; col < size; col++) {

                fixed[row][col] = !board.isEmpty(row, col);
            }
        }
    }


    /**
     * Returns true if the given cell is fixed.
     */
    public boolean isFixed(int row, int col) {
        
        return fixed[row][col];
    }

}