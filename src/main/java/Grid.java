import java.util.Arrays;
import java.util.List;

public class Grid {
    private final Cell[][] cells;
    private final int rows;
    private final int columns;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.cells = createCells();
    }

    public int numberOfRows() {
        return rows;
    }

    public int numberOfColumns() {
        return columns;
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

    public void insertLivingCell(Point point) {
        cells[point.row()][point.col()] = new Cell(true);
    }

    public void insertDeadCell(Point point) {
        cells[point.row()][point.col()] = new Cell(false);
    }

    public Cell getCell(Point point) {
        return cells[point.row()][point.col()];
    }

    public List<Cell> getNeighbours(Point point) {

        int north = point.row() - 1;
        int east = point.col() + 1;
        int south = point.row() + 1;
        int west = point.col() - 1;

        return Arrays.asList(
                getCell(new Point(north, west)),
                getCell(new Point(north, point.col())),
                getCell(new Point(north, east)),

                getCell(new Point(point.row(), west)),
                getCell(new Point(point.row(), east)),

                getCell(new Point(south, west)),
                getCell(new Point(south, point.col())),
                getCell(new Point(south, east))
        );
    }

    public int getAliveNeighbours(Point point) {
        return (int) getNeighbours(new Point(point.row(), point.col()))
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

                Cell cell = getCell(new Point(rowIndex, colIndex));
                int aliveNeighbours = getAliveNeighbours(new Point(rowIndex, colIndex));
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

                var currentCell = getCell(new Point(rowIndex, colIndex));
                int aliveNeighbours = getAliveNeighbours(new Point(rowIndex, colIndex));

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
