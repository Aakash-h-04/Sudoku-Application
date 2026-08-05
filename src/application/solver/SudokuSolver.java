package application.solver;

import domain.SudokuBoard;

/**
 * Coordinates the Sudoku solving workflow.
 *
 * This class owns the application use-case,
 * not the solving algorithm itself.
 */
public class SudokuSolver {

    private final SolverAlgorithm algorithm;

    public SudokuSolver(SolverAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @SuppressWarnings("unused")
    public boolean solve(SudokuBoard board) {

        validate(board);

        long start = System.nanoTime();

        boolean solved = algorithm.solve(board);

        long end = System.nanoTime();

        // Logging / Metrics / Statistics
        // (Version 2)

        return solved;
    }

    private void validate(SudokuBoard board) {

        if (board == null) {
            throw new IllegalArgumentException(
                    "Board cannot be null.");
        }
    }

}