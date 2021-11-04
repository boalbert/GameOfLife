package se.boalbert;

import java.util.Random;

public class Grid {
    private final Cell[][] cells;
    private final int rows;
    private final int columns;

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

    public void insertDeadCell(Point point) {
        cells[point.row()][point.col()] = new Cell(false);
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

    //todo ändra till grid
    private boolean[][] calculateNextGeneration() {
        boolean[][] nextGeneration = new boolean[numberOfRows()][numberOfColumns()];

        for (int rowIndex = 0; rowIndex < numberOfRows(); rowIndex++) {
            for (int colIndex = 0; colIndex < numberOfColumns(); colIndex++) {
                var currentPoint = new Point(rowIndex, colIndex);
                var currentCell = findCell(currentPoint);
                nextGeneration[rowIndex][colIndex] = isAliveNextGeneration(currentPoint, currentCell);
            }
        }
        return nextGeneration;
    }

    private boolean isAliveNextGeneration(Point point, Cell cell) {
        int aliveNeighbours = countAliveNeighbours(point);

        if (cell.alive() &&
                (aliveNeighbours == 2 || aliveNeighbours == 3)) return true;

        return !cell.alive()
                && aliveNeighbours == 3;
    }

    private void insertNextGenerationInBoard(boolean[][] nextGeneration) {
        for (int rowIndex = 0; rowIndex < numberOfRows(); rowIndex++) {
            for (int columnIndex = 0; columnIndex < numberOfColumns(); columnIndex++) {
                cells[rowIndex][columnIndex] = new Cell(nextGeneration[rowIndex][columnIndex]);
            }
        }
    }

    public void goToNextGeneration() {
        insertNextGenerationInBoard(calculateNextGeneration());
    }

    public void printGrid() {
        for (int rowIndex = 0; rowIndex < numberOfRows(); rowIndex++) {
            for (int colIndex = 0; colIndex < numberOfColumns(); colIndex++) {
                Point point = new Point(rowIndex, colIndex);
                if (findCell(point).alive()) System.out.print("⏣");
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
