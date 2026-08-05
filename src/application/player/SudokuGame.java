package application.player;

import java.util.Scanner;

import domain.SudokuBoard;
import util.BoardPrinter;

/**
 * Interactive Sudoku game.
 *
 * Responsibilities:
 *  - Game loop
 *  - Read player commands
 *  - Coordinate board, cursor and fixed cells
 *
 * This class NEVER validates Sudoku rules.
 * That responsibility belongs to SudokuBoard.
 */
public class SudokuGame {

    private final SudokuBoard board;

    private final Cursor cursor;

    private final FixedCellTracker fixedCells;

    private final Scanner scanner;

    public SudokuGame(SudokuBoard board, Scanner sc) {

        this.board = board;

        cursor = new Cursor(board.getSize());

        fixedCells = new FixedCellTracker(board);

        this.scanner = sc;


    }

    /**
     * Starts the interactive game.
     */
    public void play() {

        boolean running = true;
    
        while (running) {

    
            BoardPrinter.print(
                    board,
                    cursor.getRow(),
                    cursor.getCol());
    
            if (board.isSolved()) {
    
                System.out.println();
    
                System.out.println(
                        "Congratulations! Sudoku solved.");
    
                break;
            }
    
            printMenu();
    
            char command = readCommand();

            
    
            running = execute(command);
        }
    
    }

    /**
    * Executes one player command.
    *
    * @return false if the game should terminate.
    */
    private boolean execute(char command) {

        switch (command) {

            case 'k':

                quit();

                return false;

            case 'p':

                placeDigit();

                return true;

            default:

                if (!cursor.move(command)) {

                    System.out.println();

                    System.out.println("Invalid movement.");
                }

            return true;
        }
    }

    /**
     * Prints the available commands.
     */
    private void printMenu() {

        System.out.println();

        System.out.println("Move:");

        System.out.println("Q W E");

        System.out.println("A   D");

        System.out.println("Z X C");

        System.out.println();

        System.out.println("P : Place Digit");

        System.out.println("K : Quit");

        System.out.println();
    }

    /**
     * Reads next command.
     */
    private char readCommand() {

        System.out.print("Command : ");

        return Character.toLowerCase(scanner.next().charAt(0));
    }

    /**
     * Ends the game.
     */
    private void quit() {

        System.out.println();

        System.out.println("Thank You!");

    }

    /* 
    * Allows the player to place or remove a digit.
    */
    /**
    * Places or removes a digit.
    */
    private void placeDigit() {

        int row = cursor.getRow();
        int col = cursor.getCol();

        if (fixedCells.isFixed(row, col)) {

            System.out.println();

            System.out.println("This is a fixed cell.");

            return;
        }

        System.out.println();

        System.out.printf("Enter digit (0-%d): ",board.getSize());

        if (!scanner.hasNextInt()) {

            scanner.next();

            System.out.println("Invalid input.");

            return;
        }

        int digit = scanner.nextInt();

        if (digit < 0 || digit > board.getSize()) {

            System.out.println("Digit out of range.");

            return;
        }

        boolean updated = board.updateDigit(row,col,digit);

        if (!updated) {

            System.out.println();

            System.out.println("Illegal move.");
        }
    }
    

}