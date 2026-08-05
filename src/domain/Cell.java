package domain;

import java.util.Objects;

/**
 * Represents a single location on a Sudoku board.
 *
 * Immutable value object.
 */
public final class Cell {

    private final int row;
    private final int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof Cell))
            return false;

        Cell other = (Cell) obj;

        return row == other.row &&
               col == other.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "Cell[row=" + row + ", col=" + col + "]";
    }
}