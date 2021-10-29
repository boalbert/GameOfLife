public class Board {
    private final Cell[][] cells;
    private final int rows;
    private final int columns;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.cells = createCells();
    }

    public boolean isAlive(int row, int col) {
        return cells[row][col].isAlive();
    }

    private Cell[][] createCells() {
        Cell[][] cells = new Cell[rows][columns];
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
                cells[rowIndex][columnIndex] = new Cell();
            }
        }
        return cells;
    }

    public boolean setTileAlive(int row, int col) {
        return false;
    }
}
