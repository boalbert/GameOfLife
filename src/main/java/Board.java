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
        return cells[row][col].alive();
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

    public void insertLivingCell(int row, int col) {
        cells[row][col] = new Cell(true);
    }

    public void insertDeadCell(int row, int col) {
        cells[row][col] = new Cell();
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
                .filter(Cell::alive)
                .count();
    }

    private boolean[][] calculateNextState() {
        boolean[][] nextState = new boolean[rows()][cols()];

        // Loop through every row
        for (int rowIndex = 0; rowIndex < rows(); rowIndex++) {
            // Loop trough every column
            for (int colIndex = 0; colIndex < cols(); colIndex++) {

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
        boolean[][] nextState = new boolean[rows()][cols()];

        for (int rowIndex = 0; rowIndex < rows(); rowIndex++) {
            for (int colIndex = 0; colIndex < cols(); colIndex++) {

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
