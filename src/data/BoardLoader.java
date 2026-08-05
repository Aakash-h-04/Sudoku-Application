package data;

/**
 * Loads predefined Sudoku puzzles.
 */
public final class BoardLoader {

    private BoardLoader() {
        throw new AssertionError(
                "Utility class cannot be instantiated.");
    }

    /**
     * Loads the requested Sudoku board.
     */
    public static char[][] load(BoardType type) {

        switch (type) {

            case FOUR_BY_FOUR:
                return load4x4();

            case NINE_BY_NINE:
                return load9x9();

            default:
                throw new IllegalArgumentException(
                        "Unsupported board type.");
        }
    }

    private static char[][] load4x4() {

        return new char[][]{

                {'3','2','.','.'},
                {'.','4','.','3'},
                {'.','3','1','.'},
                {'2','.','.','4'}

        };
    }

    private static char[][] load9x9() {

        return new char[][]{

                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},

                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},

                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}

        };
    }

}