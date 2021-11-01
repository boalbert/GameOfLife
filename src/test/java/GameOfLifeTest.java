import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeTest {

    @Test
    void BoardTilesAreInitializedToZero() {
        Board board = new Board(8, 8);
        assertFalse(board.getCell(new Point(5, 5)).alive());
    }

    @Test
    void SettingCellAliveReturnsTrueWhenCheckingIsAliveOnCell() {
        Board board = new Board(6, 6);
        board.insertLivingCell(new Point(2, 2));
        assertTrue(board.getCell(new Point(2, 2)).alive());
    }

    @Test
    void EveryCellHasEightNeighbours() {
        Board board = new Board(8, 8);

        assertEquals(8, board.getNeighbours(new Point(5, 5)).size());
    }

    @Test
    void InitiallyEveryCellHasZeroAliveNeighbours() {
        Board board = new Board(12, 15);
        assertEquals(0, board.getAliveNeighbours(new Point(5, 5)));
    }

    @Test
    void SettingOneNeighbourAliveShouldReturnOneAliveNeighbour() {
        Board board = new Board(12, 15);
        board.insertLivingCell(new Point(4, 5));
        assertEquals(1, board.getAliveNeighbours(new Point(5, 5)));
    }

    @Test
    void SettingFourAliveNeighboursShouldReturnFourAliveNeighbours() {
        Board board = new Board(12, 15);
        board.insertLivingCell(new Point(2, 2));
        board.insertLivingCell(new Point(2, 3));
        board.insertLivingCell(new Point(2, 4));
        board.insertLivingCell(new Point(3, 2));
        assertEquals(4, board.getAliveNeighbours(new Point(3, 3)));
    }

    @Test
    void SettingCellAliveThatIsNotANeighbourShouldNotCountAsAliveNeighbour() {
        Board board = new Board(12, 15);

        board.insertLivingCell(new Point(0, 0));

        assertEquals(0, board.getAliveNeighbours(new Point(3, 3)));
    }

    @Test
    void GivenANewGameOfLifeObjectWith5RowsReturnsObjectWithAGridOfFiveRows() {
        GameOfLife gameOfLife = new GameOfLife(5, 5);
        assertEquals(5, gameOfLife.board().numberOfRows());
    }

    @Test
    void GivenAliveCellAndSettingItDeadShouldMakeTileDead() {
        Board board = new Board(5, 5);

        board.insertLivingCell(new Point(3, 3));
        board.insertDeadCell(new Point(3, 3));
        assertFalse(board.getCell(new Point(3, 3)).alive());
    }

    @Test
    @Disabled
    void CellWithZeroNeighboursDiesInNextGeneration() {
        Board board = new Board(12, 12);

        board.insertLivingCell(new Point(5, 5));
        boolean[][] booleans = board.calculateNextGeneration();
        assertFalse(booleans[3][3]);
    }

    @Test
    @Disabled
    void CellWithMoreThanThreeNeighboursDiesInTheNextGeneration() {
        Board board = new Board(8, 8);

        board.insertLivingCell(new Point(3, 3));

        board.insertLivingCell(new Point(2, 2));
        board.insertLivingCell(new Point(2, 3));
        board.insertLivingCell(new Point(2, 4));
        board.insertLivingCell(new Point(3, 2));

        board.calculateNextGeneration();

        assertFalse(board.getCell(new Point(3, 3)).alive());
    }
}