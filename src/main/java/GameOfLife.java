import com.sun.jdi.BooleanType;

public class GameOfLife {
    private Board board;

    public GameOfLife(int rows, int columns) {
        this.board = new Board(rows, columns);
    }

    public Board board() {
        return board;
    }
    /*
    Ideas:
    - When program is started ask user what size of grid it is
       - Don't create the grid just yet
    - Then ask what tiles has alive cells
        - Check so that the placement is not outside the gridsize
    - Only create the grid when printing out to console
     */
}
