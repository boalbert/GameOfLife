package se.boalbert;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeTest {

    @Test
    void GridTilesAreInitializedToDead() {
        Grid grid = new Grid(8, 8);
        var point = new Point(5, 5);
        assertFalse(grid.findCell(point).alive());
    }

    @Test
    void SettingCellAliveReturnsTrueWhenCheckingIsAliveOnCell() {
        Grid grid = new Grid(6, 6);
        var point = new Point(2, 2);
        grid.insertLivingCell(point);
        assertTrue(grid.findCell(point).alive());
    }


    @Test
    void InitiallyEveryCellHasZeroAliveNeighbours() {
        Grid grid = new Grid(12, 15);
        var point = new Point(5, 5);
        assertEquals(0, grid.countAliveNeighbours(point));
    }

    @Test
    void SettingOneNeighbourAliveShouldReturnOneAliveNeighbour() {
        Grid grid = new Grid(12, 15);

        var pointLivingCell = new Point(4, 5);
        grid.insertLivingCell(pointLivingCell);

        var pointDeadCell = new Point(5, 5);
        assertEquals(1, grid.countAliveNeighbours(pointDeadCell));
    }

    @Test
    void SettingFourAliveNeighboursShouldReturnFourAliveNeighbours() {
        Grid grid = new Grid(12, 15);

        grid.insertLivingCell(new Point(2, 2));
        grid.insertLivingCell(new Point(2, 3));
        grid.insertLivingCell(new Point(2, 4));
        grid.insertLivingCell(new Point(3, 2));

        assertEquals(4, grid.countAliveNeighbours(new Point(3, 3)));
    }

    @Test
    void SettingCellAliveThatIsNotANeighbourShouldNotCountAsAliveNeighbour() {
        Grid grid = new Grid(12, 15);

        grid.insertLivingCell(new Point(0, 0));

        assertEquals(0, grid.countAliveNeighbours(new Point(3, 3)));
    }

    @Test
    void GivenPointInTheMiddleOfGridReturnsTrue() {
        var inTheMiddle = new Point(5, 5);
        assertTrue(inTheMiddle.isInside(12, 12));
    }

    @Test
    void GivenPointOutsideGridReturnsFalse() {
        var outside = new Point(14, 14);
        assertFalse(outside.isInside(12, 12));
    }

    @Test
    void GivenPointAtEdgeReturnsTrue() {
        var onEdge = new Point(12, 12);
        assertFalse(onEdge.isInside(12, 12));
    }

    @Test
    void PointHasEightNeighbours() {
        Point point = new Point(5, 5);
        assertEquals(8, point.neighbours(point).size());
    }

    @Test
    void PointInTheMiddleOfGridHasEightNeighboursInsideGrid() {
        Grid grid = new Grid(12, 12);
        Point point = new Point(5, 5);
        assertEquals(8, point.neighboursInsideGrid(point, grid.numberOfRows(), grid.numberOfColumns()).size());
    }

    @Test
    void PointAtEdgeHasFiveNeighbours() {
        Grid grid = new Grid(12, 12);
        Point point = new Point(0, 5);
        assertEquals(5, point.neighboursInsideGrid(point, grid.numberOfRows(), grid.numberOfColumns()).size());
    }

    @Test
    void PointInCornerHasThreeNeighbours() {
        Grid grid = new Grid(12, 12);
        Point point = new Point(0, 0);
        assertEquals(3, point.neighboursInsideGrid(point, grid.numberOfRows(), grid.numberOfColumns()).size());
    }

    @Test
    void CellWithZeroNeighboursDiesInNextGeneration() {
        Grid grid = new Grid(12, 12);

        grid.insertLivingCell(new Point(5, 5));

        grid = grid.goToNextGeneration();

        assertFalse(grid.findCell(new Point(3, 3)).alive());
    }

    @Test
    void CellWithMoreThanThreeNeighboursDiesInTheNextGeneration() {
        Grid grid = new Grid(8, 8);

        grid.insertLivingCell(new Point(3, 3));

        grid.insertLivingCell(new Point(2, 2));
        grid.insertLivingCell(new Point(2, 3));
        grid.insertLivingCell(new Point(2, 4));
        grid.insertLivingCell(new Point(3, 2));


        grid = grid.goToNextGeneration();

        assertFalse(grid.findCell(new Point(5, 5)).alive());
    }

    @Test
    void DeadCellWithThreeAliveNeighboursBecomesALiveCellInTheNextGeneration() {
        Grid grid = new Grid(12, 12);

        grid.insertLivingCell(new Point(5, 4));
        grid.insertLivingCell(new Point(4, 5));
        grid.insertLivingCell(new Point(6, 5));

        grid = grid.goToNextGeneration();
        assertTrue(grid.findCell(new Point(5, 5)).alive());
    }

    @Test
    void DeadCellWithThreeAliveNeighboursIsInsertedIntoCellsInTheNextGeneration() {
        Grid grid = new Grid(12, 12);

        grid.insertLivingCell(new Point(5, 4));
        grid.insertLivingCell(new Point(4, 5));
        grid.insertLivingCell(new Point(6, 5));

        grid = grid.goToNextGeneration();
        assertTrue(grid.findCell(new Point(5, 5)).alive());
    }

    @Test
    void AliveCellWithMoreThanThreeNeighboursIsDeadInNextGeneration() {
        Grid grid = new Grid(8, 8);

        grid.insertLivingCell(new Point(3, 3));

        grid.insertLivingCell(new Point(2, 2));
        grid.insertLivingCell(new Point(2, 3));
        grid.insertLivingCell(new Point(2, 4));
        grid.insertLivingCell(new Point(3, 2));

        grid = grid.goToNextGeneration();
        assertFalse(grid.findCell(new Point(3, 3)).alive());
    }

    @Test
    void RandomizeGridReturnsDifferentAmountOfLivingCells() {

        var randomFirst = new Grid(10, 10).randomGrid(new Random());
        var randomSecond = new Grid(10, 10).randomGrid(new Random());

        assertNotEquals(Arrays.deepToString(randomFirst.cells()), Arrays.deepToString(randomSecond.cells()));
    }

    @Test
    void GivenSizeArgumentReturnsAGridWithCorrectSize() throws ParseException {
        String[] args = new String[]{"-s", "20.40"};

        Grid grid = new Grid(10, 10);
        CmdLineAction cmdLineAction = new CmdLineAction(args, grid);
        grid = cmdLineAction.setGridProperties();

        assertEquals(20, grid.numberOfRows());
        assertEquals(40, grid.numberOfColumns());
    }

    @Test
    void GivenPointsArgumentInsertsLivingCellsAtCorrectPoints() throws ParseException {
        String[] args = new String[]{"-p", "10.10,15.15,12.5"};

        Grid grid = new Grid(50, 50);
        CmdLineAction cmdLineAction = new CmdLineAction(args, grid);
        grid = cmdLineAction.setGridProperties();

        assertTrue(grid.findCell(new Point(10, 10)).alive());
        assertTrue(grid.findCell(new Point(15, 15)).alive());
        assertTrue(grid.findCell(new Point(12, 5)).alive());
    }

    @Test
    void GivenRandomArgumentRetursABoardThatDiffersFromStandardBoard() throws ParseException {
        String[] args = new String[]{"-r"};

        Grid standardGrid = new Grid(20, 20);
        Grid gridWithRandomArgument = new Grid(20, 20);

        CmdLineAction cmdLineAction = new CmdLineAction(args, gridWithRandomArgument);

        gridWithRandomArgument = cmdLineAction.setGridProperties();

        assertNotEquals(Arrays.deepToString(standardGrid.cells()), Arrays.deepToString(gridWithRandomArgument.cells()));
    }

    @Test
    void GivenArgumentGenerations88ReturnsIntegerOf88() throws ParseException {
        String[] args = new String[]{"-g", "88"};

        Grid standardGrid = new Grid(20, 20);
        CmdLineAction cmdLineAction = new CmdLineAction(args, standardGrid);

        assertEquals(88, cmdLineAction.setGenerations());
    }

    @Test
    void GivenSeveralArgumentsCombined_Size_Generations_Points_ReturnsCorrectBoard() throws ParseException {
        String[] args = new String[]{"-g", "5", "-s", "30.50", "-p", "5.10,8.12,6.1"};

        Grid grid = new Grid(20, 20);
        CmdLineAction cmdLineAction = new CmdLineAction(args, grid);

        int generations = cmdLineAction.setGenerations();
        assertEquals(5, generations);

        grid = cmdLineAction.setGridProperties();

        assertEquals(30, grid.numberOfRows());
        assertEquals(50, grid.numberOfColumns());

        assertTrue(grid.findCell(new Point(5, 10)).alive());
        assertTrue(grid.findCell(new Point(8, 12)).alive());
        assertTrue(grid.findCell(new Point(6, 1)).alive());

    }

    @Test
    void GivenHelpArgumentIsRecognized() throws ParseException {
        String[] args = new String[]{"-h"};
        Grid grid = new Grid(20, 20);
        CmdLineAction cmdLineAction = new CmdLineAction(args, grid);

        assertTrue(cmdLineAction.helpArgumentSupplied());
    }

    @Test
    void GivenMissingGenerationsArgumentGenerationsIsSetDefaultToTwenty() throws ParseException {
        String[] args = new String[]{"-r"};
        Grid grid = new Grid(20, 20);
        CmdLineAction cmdLineAction = new CmdLineAction(args, grid);

        assertEquals(20, cmdLineAction.setGenerations());
    }
}