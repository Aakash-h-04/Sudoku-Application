package application.solver;

import domain.SudokuBoard;

/**
 * Strategy interface for Sudoku solving algorithms.
 *
 * Implementations encapsulate different solving techniques,
 * such as:
 *  - MRV Backtracking
 *  - Dancing Links (DLX)
 *  - Human-style solving
 *  - Brute Force
 *
 * Implementations may modify the supplied board.
 */
public interface SolverAlgorithm {

    /**
     * Attempts to solve the given Sudoku board.
     *
     * @param board board to solve
     * @return true if a solution exists,
     *         false otherwise
     */
    boolean solve(SudokuBoard board);

}