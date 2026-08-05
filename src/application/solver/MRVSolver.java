package application.solver;

import domain.Cell;
import domain.SudokuBoard;
import util.BitUtil;

/**
 * MRV (Minimum Remaining Values) based Sudoku solver.
 *
 * Responsibilities:
 *  - Collect empty cells.
 *  - Choose the next cell using MRV.
 *  - Solve using recursive backtracking.
 *
 * This class contains only algorithm-specific state.
 * It never owns the Sudoku board.
 */
public class MRVSolver implements SolverAlgorithm {

    private Cell[] emptyCells;
    private int emptyCount;

    @Override
    public boolean solve(SudokuBoard board) {

        collectEmptyCells(board);

        return solve(board, 0);
    }

    /**
     * Collects all initially empty cells.
     */
    private void collectEmptyCells(SudokuBoard board) {

        int size = board.getSize();

        emptyCells = new Cell[size * size];
        emptyCount = 0;

        for (int row = 0; row < size; row++) {

            for (int col = 0; col < size; col++) {

                if (board.isEmpty(row, col)) {
                    emptyCells[emptyCount++] = new Cell(row, col);
                }
            }
        }
    }

    /**
     * Counts the number of set bits.
     */
    // private int countBits(int value) {

    //     int count = 0;

    //     while (value != 0) {
    //         count++;
    //         value &= (value - 1);
    //     }

    //     return count;
    // }
    /**
     * Chooses the next cell using the
     * Minimum Remaining Values heuristic.
     */
    private int findBestCell(SudokuBoard board, int currentIndex) {

        int bestIndex = currentIndex;
        int minimumChoices = Integer.MAX_VALUE;

        for (int i = currentIndex; i < emptyCount; i++) {

            Cell cell = emptyCells[i];

            int candidates = board.getCandidates(cell.getRow(),cell.getCol());

            int choices = BitUtil.bitCount(candidates);

            if (choices < minimumChoices) {

                minimumChoices = choices;
                bestIndex = i;

                if (choices == 1) {
                    break;
                }
            }
        }

        return bestIndex;
    }

    /**
     * Swaps two cells inside the empty-cell list.
     *
     * This avoids creating new arrays during recursion.
     */
    private void swapCells(int first, int second) {

        if (first == second) {
            return;
        }

        Cell temp = emptyCells[first];
        emptyCells[first] = emptyCells[second];
        emptyCells[second] = temp;
    }
    /**
     * Recursive MRV Backtracking Solver.
     */
    private boolean solve(SudokuBoard board, int index) {

        if (index == emptyCount) {
            return true;
        }
    
        int bestIndex = findBestCell(board, index);
    
        swapCells(index, bestIndex);
    
        Cell cell = emptyCells[index];
    
        int row = cell.getRow();
        int col = cell.getCol();
    
        int candidates = board.getCandidates(row, col);
    
        while (candidates != 0) {
    
            int candidate = BitUtil.nextCandidate(candidates);
    
            int digit = BitUtil.trailingZeros(candidate);
    
            board.placeDigit(row, col, digit);
    
            if (solve(board, index + 1)) {
                return true;
            }
    
            board.removeDigit(row, col);
    
            candidates = BitUtil.removeCandidate(candidates,candidate);
        }
    
        swapCells(index, bestIndex);
    
        return false;
    }
}