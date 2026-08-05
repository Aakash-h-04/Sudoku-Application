import java.util.Scanner;

public class tempUsrSolve {

    static final int N = 4;

    static final char[][] board = {
            {'3','2','.','.'},
            {'.','4','.','3'},
            {'.','3','1','.'},
            {'2','.','.','4'}
    };

    static final boolean[][] fixed = new boolean[N][N];

    static int row = 0, col = 0;
    static int remaining = 0;

    static final String RESET = "\u001B[0m";
    static final String PINK  = "\u001B[35m";

    static void printBoard() {
        System.out.println("----------");
        for (int i = 0; i < N; i++) {
            if (i == 2) System.out.println("----------");
            for (int j = 0; j < N; j++) {
                if (j == 2) System.out.print(" | ");

                if (i == row && j == col)
                    System.out.print(PINK + board[i][j] + RESET + " ");
                else
                    System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("----------");
    }

    static boolean inside() {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    static boolean move(char ch) {
        switch (Character.toLowerCase(ch)) {
            case 'q': row--; col--; break;
            case 'w': row--; break;
            case 'e': row--; col++; break;
            case 'a': col--; break;
            case 'd': col++; break;
            case 'z': row++; col--; break;
            case 'x': row++; break;
            case 'c': row++; col++; break;
            default: return false;
        }
        return true;
    }

    static boolean valid(int r, int c) {
        char d = board[r][c];
        if (d == '.') return false;

        for (int i = 0; i < N; i++) {
            if (i != c && board[r][i] == d) return false;
            if (i != r && board[i][c] == d) return false;
        }

        int sr = (r / 2) * 2;
        int sc = (c / 2) * 2;

        for (int i = sr; i < sr + 2; i++)
            for (int j = sc; j < sc + 2; j++)
                if (!(i == r && j == c) && board[i][j] == d)
                    return false;

        return true;
    }

    static boolean solved() {
        if (remaining != 0) return false;

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (!valid(i, j))
                    return false;

        return true;
    }

    static void place(Scanner sc) {

        if (fixed[row][col]) {
            System.out.println("Fixed cell!");
            return;
        }

        System.out.print("Enter number (0-4): ");

        if (!sc.hasNextInt()) {
            sc.next();
            System.out.println("Invalid input!");
            return;
        }

        int n = sc.nextInt();

        if (n < 0 || n > 4) {
            System.out.println("Invalid number!");
            return;
        }

        boolean wasEmpty = board[row][col] == '.';
        char old = board[row][col];

        board[row][col] = (n == 0) ? '.' : (char) ('0' + n);

        if (n != 0 && !valid(row, col)) {
            board[row][col] = old;
            System.out.println("Invalid move!");
            return;
        }

        if (wasEmpty && n != 0) remaining--;
        if (!wasEmpty && n == 0) remaining++;
    }

    static void clearScreen() {

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void play() {

        Scanner sc = new Scanner(System.in);

        while (true) {

            clearScreen();

            printBoard();

            if (solved()) {
                System.out.println("\nCongratulations! Sudoku solved.");
                break;
            }

            System.out.println("\nMove : Q W E");
            System.out.println("       A   D");
            System.out.println("       Z X C");
            System.out.println("P = Place Number");
            System.out.println("K = Quit");

            System.out.print("\nCommand : ");
            char ch = Character.toLowerCase(sc.next().charAt(0));

            if (ch == 'k') {
                System.out.println("Thank You!");
                break;
            }

            if (ch == 'p') {
                place(sc);
                continue;
            }

            int pr = row;
            int pc = col;

            if (!move(ch)) {
                System.out.println("Invalid command!");
                continue;
            }

            if (!inside()) {
                row = pr;
                col = pc;
                System.out.println("Invalid direction!");
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String garbage = sc.nextLine();

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (board[i][j] == '.')
                    remaining++;
                else
                    fixed[i][j] = true;

        play();
    }
}


