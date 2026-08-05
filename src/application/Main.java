package application;

import java.util.Scanner;

import application.player.SudokuGame;
import application.solver.MRVSolver;
import application.solver.SudokuSolver;

import data.BoardLoader;
import data.BoardType;

import domain.SudokuBoard;

import util.BoardPrinter;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            printMainMenu();

            switch (readChoice()) {

                case 1:
                    playMode();
                    break;

                case 2:
                    solveMode();
                    break;

                case 0:
                    System.out.println("\nGood Bye!");
                    return;

                default:
                    System.out.println("\nInvalid choice!");
            }
        }
    }

    /**
     * Starts User Play Mode.
     */
    private static void playMode() {

        SudokuBoard board = createBoard();

        SudokuGame game = new SudokuGame(board, scanner);

        game.play();
    }

    /**
     * Starts Computer Solve Mode.
     */
    private static void solveMode() {

        SudokuBoard board = createBoard();

        SudokuSolver solver =
                new SudokuSolver(new MRVSolver());

        System.out.println("\nInitial Board\n");

        BoardPrinter.print(board);

        if (solver.solve(board)) {

            System.out.println("\nSolved Board\n");

            BoardPrinter.print(board);
        }
        else {

            System.out.println("\nNo Solution Exists.");
        }
    }

    /**
     * Lets the user choose which board to load.
     */
    private static SudokuBoard createBoard() {

        while (true) {

            System.out.println();

            System.out.println("Choose Board");

            System.out.println("1. 4 × 4");

            System.out.println("2. 9 × 9");

            System.out.print("\nChoice : ");

            switch (readChoice()) {

                case 1:

                    return new SudokuBoard(
                        BoardLoader.load(
                            BoardType.FOUR_BY_FOUR
                        )
                    );

                case 2:

                    return new SudokuBoard(
                        BoardLoader.load(
                            BoardType.NINE_BY_NINE
                        )
                    );

                default:

                System.out.println("\nInvalid board selection!");
            }
        }
    }

    /**
     * Prints the main menu.
     */
    private static void printMainMenu() {

        System.out.println();

        System.out.println("==============================");

        System.out.println("      Sudoku Application");

        System.out.println("==============================");

        System.out.println("1. Play Sudoku");

        System.out.println("2. Solve Sudoku");

        System.out.println("0. Exit");

        System.out.print("\nChoice : ");
    }

    /**
     * Reads an integer choice from the user.
     */
    private static int readChoice() {

        if (!scanner.hasNextInt()) {

            scanner.next();

            return -1;
        }

        return scanner.nextInt();
    }
}