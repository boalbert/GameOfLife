import java.util.Arrays;
import java.util.List;

public class Board {
    private final Cell[][] cellGrid;
    private final int rows;
    private final int columns;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.cellGrid = createCells();
    }

    public int numberOfRows() {
        return rows;
    }

    public int numberOfColumns() {
        return columns;
    }

    public boolean isAlive(int row, int col) {
        return cellGrid[row][col].alive();
    }

    private Cell[][] createCells() {
        Cell[][] cells = new Cell[rows][columns];
        for (int rowIndex = 0; rowIndex < numberOfRows(); rowIndex++) {
            for (int columnIndex = 0; columnIndex < numberOfColumns(); columnIndex++) {
                cells[rowIndex][columnIndex] = new Cell(false);
            }
        }
        return cells;
    }

    public void insertLivingCell(int row, int col) {
        cellGrid[row][col] = new Cell(true);
    }

    public void insertDeadCell(int row, int col) {
        cellGrid[row][col] = new Cell(false);
    }

    public Cell getCell(int row, int col) {
        return cellGrid[row][col];
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
                .filter(Cell::alive)
                .count();
    }

    private boolean[][] calculateNextState() {
        boolean[][] nextState = new boolean[numberOfRows()][numberOfColumns()];

        // Loop through every row
        for (int rowIndex = 0; rowIndex < numberOfRows(); rowIndex++) {
            // Loop trough every column
            for (int colIndex = 0; colIndex < numberOfColumns(); colIndex++) {

                Cell cell = getCell(rowIndex, colIndex);
                int aliveNeighbours = getAliveNeighbours(rowIndex, colIndex);
                boolean isAliveInNextState =
                        cell.alive() && aliveNeighbours == 2 || aliveNeighbours == 3;
                nextState[rowIndex][colIndex] = isAliveInNextState;

            }
        }
        return nextState;
    }

    public boolean[][] calculateNextGeneration() {
        boolean[][] nextState = new boolean[numberOfRows()][numberOfColumns()];

        for (int rowIndex = 0; rowIndex < numberOfRows(); rowIndex++) {
            for (int colIndex = 0; colIndex < numberOfColumns(); colIndex++) {

                var currentCell = getCell(rowIndex, colIndex);
                int aliveNeighbours = getAliveNeighbours(rowIndex, colIndex);

                if (currentCell.alive()) {
                    if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                        nextState[rowIndex][colIndex] = true;
                    }
                }
            }
        }
        return nextState;
    }
}
