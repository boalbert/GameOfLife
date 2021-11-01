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

    public int countAliveNeighbours(Point point) {
        return (int) point.neighboursInsideGrid(point, numberOfRows(), numberOfColumns())
                .stream()
                .map(this::getCell)
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
                int aliveNeighbours = countAliveNeighbours(new Point(rowIndex, colIndex));
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
                int aliveNeighbours = countAliveNeighbours(new Point(rowIndex, colIndex));

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
