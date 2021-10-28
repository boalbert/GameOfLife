import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeTest {

    @Test
    void setAlive() {
        GameOfLife gameOfLife = new GameOfLife();

        assertTrue(gameOfLife.setAlive(1, 2));
    }

    @Test
    void getAliveCells() {
        GameOfLife gameOfLife = new GameOfLife();
        gameOfLife.setAlive(1, 1);
        gameOfLife.setAlive(1, 1);
        gameOfLife.setAlive(1, 1);

        assertEquals(1, gameOfLife.getAliveCells().size());
    }

}