import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeTest {

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
        Grid grid = new Grid(12, 12);
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
    void GridTilesAreInitializedToZero() {
        Grid grid = new Grid(8, 8);
        var point = new Point(5, 5);
        assertFalse(grid.getCell(point).alive());
    }

    @Test
    void SettingCellAliveReturnsTrueWhenCheckingIsAliveOnCell() {
        Grid grid = new Grid(6, 6);
        var point = new Point(2, 2);
        grid.insertLivingCell(point);
        assertTrue(grid.getCell(point).alive());
    }

    @Test
    void EveryCellHasEightNeighbours() {
        Grid grid = new Grid(8, 8);
        var point = new Point(5, 5);
        assertEquals(8, grid.getNeighbours(point).size());
    }

    @Test
    void InitiallyEveryCellHasZeroAliveNeighbours() {
        Grid grid = new Grid(12, 15);
        var point = new Point(5, 5);
        assertEquals(0, grid.getAliveNeighbours(point));
    }

    @Test
    void SettingOneNeighbourAliveShouldReturnOneAliveNeighbour() {
        Grid grid = new Grid(12, 15);

        var pointLivingCell = new Point(4, 5);
        grid.insertLivingCell(pointLivingCell);

        var pointDeadCell = new Point(5, 5);
        assertEquals(1, grid.getAliveNeighbours(pointDeadCell));
    }

    @Test
    void SettingFourAliveNeighboursShouldReturnFourAliveNeighbours() {
        Grid grid = new Grid(12, 15);

        grid.insertLivingCell(new Point(2, 2));
        grid.insertLivingCell(new Point(2, 3));
        grid.insertLivingCell(new Point(2, 4));
        grid.insertLivingCell(new Point(3, 2));

        assertEquals(4, grid.getAliveNeighbours(new Point(3, 3)));
    }

    @Test
    void SettingCellAliveThatIsNotANeighbourShouldNotCountAsAliveNeighbour() {
        Grid grid = new Grid(12, 15);

        grid.insertLivingCell(new Point(0, 0));

        assertEquals(0, grid.getAliveNeighbours(new Point(3, 3)));
    }

    @Test
    void GivenANewGameOfLifeObjectWith5RowsReturnsObjectWithAGridOfFiveRows() {
        GameOfLife gameOfLife = new GameOfLife(5, 5);
        assertEquals(5, gameOfLife.grid().numberOfRows());
    }

    @Test
    void GivenAliveCellAndSettingItDeadShouldMakeTileDead() {
        Grid grid = new Grid(5, 5);

        var point = new Point(3, 3);

        grid.insertLivingCell(point);
        grid.insertDeadCell(point);

        assertFalse(grid.getCell(point).alive());
    }

    @Test
    @Disabled
    void CellWithZeroNeighboursDiesInNextGeneration() {
        Grid grid = new Grid(12, 12);

        grid.insertLivingCell(new Point(5, 5));
        boolean[][] booleans = grid.calculateNextGeneration();
        assertFalse(booleans[3][3]);
    }

    @Test
    @Disabled
    void CellWithMoreThanThreeNeighboursDiesInTheNextGeneration() {
        Grid grid = new Grid(8, 8);

        grid.insertLivingCell(new Point(3, 3));

        grid.insertLivingCell(new Point(2, 2));
        grid.insertLivingCell(new Point(2, 3));
        grid.insertLivingCell(new Point(2, 4));
        grid.insertLivingCell(new Point(3, 2));

        grid.calculateNextGeneration();

        assertFalse(grid.getCell(new Point(3, 3)).alive());
    }
}