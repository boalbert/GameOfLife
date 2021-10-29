import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeTest {

    @Test
    void SetAliveCellWhereThereExistsNoCellReturnsTrue() {
        GameOfLife gameOfLife = new GameOfLife();

        assertTrue(gameOfLife.setAlive(1, 2));
    }

    @Test
    void WhenAddingACellWhereThereAlreadyExistsACellReturnFalse() {
        GameOfLife gameOfLife = new GameOfLife();
        gameOfLife.setAlive(1, 2);

        assertFalse(gameOfLife.setAlive(1, 2));
    }

    @Test
    void AddingThreeAliveCellsInUniquePlacesAddsThreeCellsToList() {
        GameOfLife gameOfLife = new GameOfLife();

        gameOfLife.setAlive(1, 1);
        gameOfLife.setAlive(2, 1);
        gameOfLife.setAlive(3, 1);

        assertEquals(3, gameOfLife.getAliveCells().size());
    }

    @Test
    void NoCellsAliveAndCheckingForAliveCellsReturnsEmptyList() {
        GameOfLife gameOfLife = new GameOfLife();
        assertTrue(gameOfLife.getAliveCells().isEmpty());
    }

    @Test
    void BoardTilesAreInitializedToZero(){
        Board board = new Board(8, 8);
        assertEquals(0, board.tiles[5][5]);
    }
}