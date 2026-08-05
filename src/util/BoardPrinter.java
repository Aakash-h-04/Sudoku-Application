package util;

import domain.SudokuBoard;

/**
 * Responsible only for displaying a Sudoku board.
 *
 * It knows nothing about:
 *  - solving
 *  - loading
 *  - bit masks
 *  - algorithms
 */
public final class BoardPrinter {

    private BoardPrinter() {
        // Utility class
    }

    public static void print(SudokuBoard board) {

        int size = board.getSize();
        int boxSize = board.getBoxSize();

        String separator = buildSeparator(size, boxSize);

        System.out.println(separator);

        for (int row = 0; row < size; row++) {

            if (row > 0 && row % boxSize == 0) {
                System.out.println(separator);
            }

            for (int col = 0; col < size; col++) {

                if (col > 0 && col % boxSize == 0) {
                    System.out.print("| ");
                }

                System.out.print(board.getDigit(row, col) + " ");
            }

            System.out.println();
        }

        System.out.println(separator);
    }

    private static String buildSeparator(int size, int boxSize) {

        int length = size * 2 + boxSize;

        StringBuilder separator = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            separator.append('-');
        }

        return separator.toString();
    }

    public static void print(SudokuBoard board, int currentRow, int currentCol) {

        clearScreen();

        int size = board.getSize();
        int boxSize = board.getBoxSize();

        String separator = buildSeparator(size, boxSize);

        final String RESET = "\u001B[0m";
        final String PINK  = "\u001B[35m";

        System.out.println(separator);

        for (int row = 0; row < size; row++) {

            if (row > 0 && row % boxSize == 0) {
                System.out.println(separator);
            }

            for (int col = 0; col < size; col++) {

                if (col > 0 && col % boxSize == 0) {
                    System.out.print("| ");
                }

                if (row == currentRow && col == currentCol) {

                    System.out.print(
                        PINK +
                        board.getDigit(row, col) +
                        RESET +
                        " "
                    );
                }
                else {

                    System.out.print(board.getDigit(row, col) + " ");
                }
            }

            System.out.println();
        }

        System.out.println(separator);
    }

    private static void clearScreen() {

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}