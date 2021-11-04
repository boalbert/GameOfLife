package se.boalbert;

import java.util.Random;

public class Grid {
    private final int rows;
    private final int columns;
    private Cell[][] cells;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.cells = initializeGridWithDeadCells();
    }

    public int numberOfRows() {
        return rows;
    }

    public int numberOfColumns() {
        return columns;
    }

    private Cell[][] initializeGridWithDeadCells() {
        Cell[][] newGrid = new Cell[numberOfRows()][numberOfColumns()];

        for (int rowIndex = 0; rowIndex < numberOfRows(); rowIndex++) {
            for (int columnIndex = 0; columnIndex < numberOfColumns(); columnIndex++) {
                newGrid[rowIndex][columnIndex] = new Cell(false);
            }
        }
        return newGrid;
    }

    public void insertLivingCell(Point point) {
        cells[point.row()][point.col()] = new Cell(true);
    }

    public Cell findCell(Point point) {
        return cells[point.row()][point.col()];
    }

    public int countAliveNeighbours(Point point) {
        return (int) point.neighboursInsideGrid(point, numberOfRows(), numberOfColumns())
                .stream()
                .map(this::findCell)
                .filter(Cell::alive)
                .count();
    }

    private Cell[][] calculateNextGeneration() {
        Cell[][] nextGenerationOfCells = new Cell[numberOfRows()][numberOfColumns()];

        for (int rowIndex = 0; rowIndex < numberOfRows(); rowIndex++) {
            for (int colIndex = 0; colIndex < numberOfColumns(); colIndex++) {

                var currentPoint = new Point(rowIndex, colIndex);
                var currentCell = findCell(currentPoint);

                nextGenerationOfCells[rowIndex][colIndex] = isAliveNextGeneration(currentPoint, currentCell);
            }
        }
        return nextGenerationOfCells;
    }

    private Cell isAliveNextGeneration(Point point, Cell cell) {
        int aliveNeighbours = countAliveNeighbours(point);

        if (cell.alive() && (aliveNeighbours == 2 || aliveNeighbours == 3)) return new Cell(true);

        if (!cell.alive() && aliveNeighbours == 3) return new Cell(true);

        return new Cell(false);
    }

    public void goToNextGeneration() {
        cells = calculateNextGeneration();
    }

    public void printGrid() {
        for (int rowIndex = 0; rowIndex < numberOfRows(); rowIndex++) {
            for (int colIndex = 0; colIndex < numberOfColumns(); colIndex++) {
                Point point = new Point(rowIndex, colIndex);
                if (findCell(point).alive()) System.out.print("*");
                else System.out.print(" ");
            }
            System.out.println();
        }
    }

    public void randomStartBoard(Random random) {
        for (int rowIndex = 0; rowIndex < numberOfRows(); rowIndex++)
            for (int columnIndex = 0; columnIndex < numberOfColumns(); columnIndex++) {
                cells[rowIndex][columnIndex] = new Cell(random.nextBoolean());
            }
    }

}
