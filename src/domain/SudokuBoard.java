package domain;
import util.MathUtil;
import util.BitUtil;
/**
 * Aggregate Root representing a Sudoku board.
 *
 * Responsibilities:
 *  - Own the board state.
 *  - Maintain row/column/box bit masks.
 *  - Validate placements.
 *  - Hide internal representation from clients.
 */
public class SudokuBoard {

    public static final char EMPTY = '.';

    private final char[][] grid;

    private final int size;
    private final int boxSize;
    private final int validMask;

    private final int[] rowMask;
    private final int[] colMask;
    private final int[] boxMask;

    public SudokuBoard(char[][] initialGrid) {

        BoardValidator.validate(initialGrid);

        this.grid = deepCopy(initialGrid);

        this.size = grid.length;
        this.boxSize = MathUtil.sqrt(size);

        this.validMask = ((1 << (size + 1)) - 1) ^ 1;

        this.rowMask = new int[size];
        this.colMask = new int[size];
        this.boxMask = new int[size];

        initializeBoard();
    }

    public int getSize() {
        return size;
    }

    public int getBoxSize() {
        return boxSize;
    }

    public char getDigit(int row, int col) {
        return grid[row][col];
    }

    public boolean isEmpty(int row, int col) {
        return grid[row][col] == EMPTY;
    }

    /**
     * Returns true if placing the given digit at the given cell
     * does not violate Sudoku constraints.
     */
    public boolean canPlaceDigit(int row, int col, int digit) {

        if (!isEmpty(row, col)) {
            return false;
        }

        int mask = BitUtil.bit(digit);
        int box = getBoxIndex(row, col);

        return (rowMask[row] & mask) == 0
                && (colMask[col] & mask) == 0
                && (boxMask[box] & mask) == 0;
    }

    public boolean placeDigitIfValid(int row, int col, int digit) {

        if (!canPlaceDigit(row, col, digit)) {
            return false;
        }

        placeDigit(row, col, digit);

        return true;
    }

        /**
     * Places or removes a digit.
     *
     * digit == 0 removes the current digit.
     *
     * @return true if the operation succeeds.
     */
    public boolean updateDigit(int row,int col,int digit) {

        if (digit == 0) {

            removeDigit(row, col);

            return true;
        }   

        return placeDigitIfValid(row, col, digit);
    }
    

    private boolean canInitializeDigit(int row, int col, int digit) {

        int mask = BitUtil.bit(digit);
        int box = getBoxIndex(row, col);
    
        return (rowMask[row] & mask) == 0
                && (colMask[col] & mask) == 0
                && (boxMask[box] & mask) == 0;
    }

    /**
     * Places a digit onto the board.
     *
     * Pre-condition:
     *      canPlaceDigit(...) == true
     */
    public void placeDigit(int row, int col, int digit) {

        if (!canPlaceDigit(row, col, digit)) {
            throw new IllegalArgumentException(
                    "Illegal placement at (" + row + ", " + col + ")");
        }
    
        applyDigit(row, col, digit);
    }

    private void applyDigit(int row, int col, int digit) {

        int mask = BitUtil.bit(digit);
        int box = getBoxIndex(row, col);
    
        grid[row][col] = MathUtil.toChar(digit);
    
        rowMask[row] = BitUtil.setMask(rowMask[row], mask);
        colMask[col] = BitUtil.setMask(colMask[col], mask);
        boxMask[box] = BitUtil.setMask(boxMask[box], mask);

    }

    /**
     * Removes a previously placed digit.
     */
    public void removeDigit(int row, int col) {

        if (isEmpty(row, col)) {
            return;
        }
    
        removePlacedDigit(row, col);
    }

    private void removePlacedDigit(int row, int col) {

        int digit = MathUtil.toDigit(grid[row][col]);
    
        int mask = BitUtil.bit(digit);
        int box = getBoxIndex(row, col);
    
        grid[row][col] = EMPTY;
    
        rowMask[row] = BitUtil.clearMask(rowMask[row], mask);
        colMask[col] = BitUtil.clearMask(colMask[col], mask);
        boxMask[box] = BitUtil.clearMask(boxMask[box], mask);
    }

    /**
     * Returns the candidate bit-mask for a cell.
     */
    public int getCandidates(int row, int col) {

        if (!isEmpty(row, col)) {
            return 0;
        }

        int box = getBoxIndex(row, col);

        return ~(rowMask[row]
                | colMask[col]
                | boxMask[box]) & validMask;
    }

    /**
     * Returns true when there are no empty cells.
     *
     * NOTE:
     * This does NOT validate correctness.
     * It only checks completeness.
     */
    public boolean isFilled() {

        for (int row = 0; row < size; row++) {

            for (int col = 0; col < size; col++) {

                if (isEmpty(row, col)) {
                    return false;
                }
            }
        }

        return true;
    }
    // ==========================================================
    // Initialization
    // ==========================================================

    private void initializeBoard() {

        for (int row = 0; row < size; row++) {
    
            for (int col = 0; col < size; col++) {
    
                if (isEmpty(row, col)) {
                    continue;
                }
    
                int digit = MathUtil.toDigit(grid[row][col]);
    
                if (!canInitializeDigit(row, col, digit)) {
                    throw new IllegalArgumentException(
                            "Invalid Sudoku configuration.");
                }
    
                applyDigit(row, col, digit);
            }
        }
    }


    // ==========================================================
    // Utility
    // ==========================================================

    private char[][] deepCopy(char[][] source) {

        char[][] copy = new char[source.length][source.length];

        // for (int i = 0; i < source.length; i++) {
        //     copy[i] = source[i].clone();
        // }

        for(int i=0 ; i<source.length ; i++){
            for(int j=0 ; j<source[i].length ; j++){
                copy[i][j] = source[i][j];
            }
        }

        return copy;
    }

    private int getStartRowIndex(int row, int col){
        return (row/boxSize) * boxSize;
    }

    private int getStartColIndex(int row, int col){
        return (col/boxSize) * boxSize;
    }

    private int getBoxIndex(int row, int col) {
        return (row / boxSize) * boxSize + (col / boxSize);
    }

    /**
    * Returns true if the Sudoku is completely solved.
    */
    public boolean isSolved() {

        return isFilled() && isValid();
    }

    /**
    * Returns true if the current board satisfies
    * all Sudoku constraints.
    */
    public boolean isValid() {

        for (int row = 0; row < size; row++) {

            for (int col = 0; col < size; col++) {

                if (isEmpty(row, col)) {
                    continue;
                }

                if (!isValidDigit(row, col)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
    * Checks whether the digit currently stored in
    * the given cell violates Sudoku rules.
    */
    private boolean isValidDigit(int row, int col) {

        char digit = getDigit(row, col);

        // Row
        for (int c = 0; c < size; c++) {

            if (c != col &&
                getDigit(row, c) == digit) {

                return false;
            }
        }

        // Column
        for (int r = 0; r < size; r++) {

            if (r != row &&
                getDigit(r, col) == digit) {

                return false;
            }
        }

        // Box
        int startRow = (row / boxSize) * boxSize;
        int startCol = (col / boxSize) * boxSize;

        for (int r = startRow ; r < startRow + boxSize ; r++) {

            for (int c = startCol ; c < startCol + boxSize ; c++) {

                if (r == row &&c == col) {

                    continue;
                }

                if (getDigit(r, c) == digit) {
                    return false;
                }
            }
        }

        return true;
    }

}