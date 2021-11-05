package se.boalbert;

import java.util.Random;

public class Grid {
    private final int rows;
    private final int columns;
    private final Cell aliveCell;
    private final Cell deadCell;
    private Cell[][] cells;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.aliveCell = new Cell(true);
        this.deadCell = new Cell(false);
        this.cells = initializeGridWithDeadCells();
    }

    public Cell[][] cells() {
        return cells;
    }

    private void cells(Cell[][] cells) {
        this.cells = cells;
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
            for (int colIndex = 0; colIndex < numberOfRows(); colIndex++) {
                newGrid[rowIndex][colIndex] = deadCell;
            }
        }

        return newGrid;
    }

    public Cell findCell(Point point) {
        return cells()[point.row()][point.col()];
    }

    public void insertLivingCell(Point point) {
        cells()[point.row()][point.col()] = aliveCell;
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

        if (cell.alive() && (aliveNeighbours == 2 || aliveNeighbours == 3)) return aliveCell;

        if (!cell.alive() && aliveNeighbours == 3) return aliveCell;

        return deadCell;
    }

    public Grid goToNextGeneration() {
        Grid nextGenerationGrid = new Grid(numberOfRows(), numberOfColumns());
        nextGenerationGrid.cells(calculateNextGeneration());
        return nextGenerationGrid;
    }

    public void printGrid() {
        for (int rowIndex = 0; rowIndex < numberOfRows(); rowIndex++) {
            for (int colIndex = 0; colIndex < numberOfColumns(); colIndex++)
                System.out.print(findCell(new Point(rowIndex, colIndex)));
            System.out.println();
        }
    }

    private Cell[][] randomizeCells(Random random) {
        Cell[][] randomGrid = new Cell[numberOfRows()][numberOfColumns()];

        for (int rowIndex = 0; rowIndex < numberOfRows(); rowIndex++)
            for (int columnIndex = 0; columnIndex < numberOfColumns(); columnIndex++) {
                if (random.nextBoolean()) randomGrid[rowIndex][columnIndex] = aliveCell;
                else randomGrid[rowIndex][columnIndex] = deadCell;
            }

        return randomGrid;
    }

    public Grid randomGrid(Random random) {
        Grid randomGrid = new Grid(numberOfRows(), numberOfColumns());
        randomGrid.cells(randomizeCells(random));
        return randomGrid;
    }
}
