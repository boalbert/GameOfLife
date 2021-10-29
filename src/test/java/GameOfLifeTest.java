import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeTest {

    @Test
    void NoCellsAliveAndCheckingForAliveCellsReturnsEmptyList() {
        GameOfLife gameOfLife = new GameOfLife();
        assertTrue(gameOfLife.getAliveCells().isEmpty());
    }

    @Test
    void BoardTilesAreInitializedToZero(){
        Board board = new Board(8, 8);
        assertFalse(board.isAlive(5,5));
    }

    @Test
    void SettingCellAliveReturnsTrueWhenCheckingIsAliveOnCell() {
        Board board = new Board(6, 6);
        assertTrue(board.setTileAlive(2,2));

    }

}