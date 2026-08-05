package domain;

import util.BitUtil;
import util.MathUtil;

/**
 * Validates Sudoku boards before they become domain objects.
 *
 * This class is responsible only for validation.
 */
public final class BoardValidator {

    private BoardValidator() {
        throw new AssertionError(
                "Utility class cannot be instantiated.");
    }

    /**
     * Validates whether the given board satisfies Sudoku rules.
     */
    public static void validate(char[][] board) {

        validateStructure(board);

        int size = board.length;
        int boxSize = MathUtil.sqrt(size);

        int[] rowMask = new int[size];
        int[] colMask = new int[size];
        int[] boxMask = new int[size];

        for (int row = 0; row < size; row++) {

            for (int col = 0; col < size; col++) {

                char value = board[row][col];

                if (value == SudokuBoard.EMPTY) {
                    continue;
                }

                validateDigit(value, size);

                int digit = value - '0';
                int mask = BitUtil.bit(digit);

                int box = (row / boxSize) * boxSize + (col / boxSize);

                if (BitUtil.containsMask(rowMask[row], mask))
                    throw new IllegalArgumentException("Duplicate digit @Row(" + row + "): " + value + " found at (" + row + ", " + col + ").");
                
                if (BitUtil.containsMask(colMask[col], mask))
                    throw new IllegalArgumentException("Duplicate digit @Col(" + col + "): " +  value + " found at (" + row + ", " + col + ").");
                
                if (BitUtil.containsMask(boxMask[box], mask))
                    throw new IllegalArgumentException("Duplicate digit @Box(" + box + "): " +  value + " found at (" + row + ", " + col + ").");


                rowMask[row] = BitUtil.setMask(rowMask[row], mask);
                colMask[col] = BitUtil.setMask(colMask[col], mask);
                boxMask[box] = BitUtil.setMask(boxMask[box], mask);
            }
        }
    }

    /**
     * Validates board dimensions.
     */
    private static void validateStructure(char[][] board) {

        if (board == null)
            throw new IllegalArgumentException(
                    "Board cannot be null.");

        int size = board.length;

        if (size != 4 && size != 9)
            throw new IllegalArgumentException(
                    "Only 4x4 and 9x9 boards supported.");

        for (char[] row : board) {

            if (row == null || row.length != size)
                throw new IllegalArgumentException(
                        "Board must be square.");
        }
    }

    /**
     * Validates digit range.
     */
    private static void validateDigit(char digit,int boardSize) {

        if (digit < '1' || digit > ('0' + boardSize)) {

            throw new IllegalArgumentException("Invalid digit : " + digit);
        }
    }

}