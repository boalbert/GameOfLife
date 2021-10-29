import java.util.Arrays;
import java.util.List;

public class Board {
    private final Cell[][] cells;
    private final int rows;
    private final int columns;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.cells = createCells();
    }

    public int rows() {
        return rows;
    }

    public int cols() {
        return columns;
    }

    public boolean isAlive(int row, int col) {
        return cells[row][col].isAlive();
    }

    private Cell[][] createCells() {
        Cell[][] cells = new Cell[rows][columns];
        for (int rowIndex = 0; rowIndex < rows(); rowIndex++) {
            for (int columnIndex = 0; columnIndex < cols(); columnIndex++) {
                cells[rowIndex][columnIndex] = new Cell();
            }
        }
        return cells;
    }

    public void setTileAlive(int row, int col) {
        cells[row][col].setAlive();
    }

    public void setTileDead(int row, int col) {
        cells[row][col].setDead();
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public List<Cell> getNeighbours(int row, int col) {
        int north = row - 1;
        int east = col + 1;
        int south = row + 1;
        int west = col - 1;

        return Arrays.asList(
                getCell(north, west),
                getCell(north, col),
                getCell(north, east),

                getCell(row, west),
                getCell(row, east),

                getCell(south, west),
                getCell(south, col),
                getCell(south, east)
        );
    }

    public int getAliveNeighbours(int row, int col) {
        return (int) getNeighbours(row, col)
                .stream()
                .filter(Cell::isAlive)
                .count();
    }

    public void nextGeneration() {
        for (int rowIndex = 0; rowIndex < rows(); rowIndex++) {
            for (int colIndex = 0; colIndex < cols(); colIndex++) {
                if (getCell(rowIndex, colIndex).isAlive()) {
                    int aliveNeighbours = getAliveNeighbours(rowIndex, colIndex);
                    if (aliveNeighbours == 0) {
                        setTileDead(rowIndex, colIndex);
                    }
                }
            }
        }
    }
}
